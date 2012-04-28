package de.najidev.mensaupb.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.entity.MenuRepository;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

public class PrepareMenuRepositoryTask extends AsyncTask<Void, Void, Void>
{
	TabActivity activity;
	Context applicationContext;
	MenuRepository menuRepository;
	ProgressDialog dialog;

	protected final String charsetOriginal = "windows-1252";
	protected final String charsetWanted   = "utf-8";
	
	public PrepareMenuRepositoryTask(final TabActivity activity,
			Context applicationContext,
			MenuRepository menuRepository)
	{
		this.activity           = activity;
		this.applicationContext = applicationContext;
		this.menuRepository     = menuRepository;
		
		this.dialog             = new ProgressDialog(activity);
		this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.dialog.setMessage("Download des Mensaplans...");
		
		this.dialog.setOnCancelListener(new OnCancelListener() {
			
			public void onCancel(DialogInterface dialog)
			{
				activity.finish();
			}
		});
	}
	
	@Override
	protected void onPreExecute()
	{
		this.dialog.show();
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		
		if (dialog.isShowing())
			dialog.dismiss();

		// following will switch to another tab and back. this definitely will throw a onTabChanged and the tab will be repainted
		int day = this.applicationContext.getCurrentDate().getDay();
		activity.getTabHost().setCurrentTab(day % (this.applicationContext.getAvailableDates().length - 1));
		activity.getTabHost().setCurrentTab(day - 1);
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		// a list, where we will save all results
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> tmp;

		for (String location : this.applicationContext.getAvailableLocations().values())
		{
			tmp = this.parseXML(this.downloadFile("http://www.studentenwerk-pb.de/fileadmin/xml/" + location + ".xml"));
			
			for (Menu menu : tmp)
				menu.setLocation(location);
			
			// add to menu list
			menus.addAll(tmp);
					
		}

		// put results to repository
		this.menuRepository.persistMenus(menus);
		
		return null;
	}
	
	public InputSource downloadFile(String url)
	{
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpGet(url));

			BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), charsetOriginal)
					);
			StringBuilder sb = new StringBuilder();

			String l = br.readLine().toLowerCase().replace(charsetOriginal, charsetWanted);
			sb.append(l + "\n");
			while ((l = br.readLine()) != null)
				sb.append(l);

			br.close();
			return new InputSource(new StringReader(sb.toString()));
		}
		catch (Exception ignored)
		{
			return null;
		}
	}

	protected List<Menu> parseXML(InputSource is)
	{
		List<Menu> list = new ArrayList<Menu>();
		// parse file
		try
		{
			Document document = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(is);

			NodeList dayList = document.getElementsByTagName("tag");

			for (int i = 0; i < dayList.getLength(); i++)
			{
				Date date = null;
				for (int k = 0; k < dayList.item(i).getChildNodes().getLength(); k++)
				{
					Node node = dayList.item(i).getChildNodes().item(k);
					if (node.getNodeName().equals("datum"))
					{
						date = new SimpleDateFormat("dd.MM.yyyy").parse(node.getTextContent());
					}					
					else if (node.getNodeName().equals("menue"))
					{
						NodeList menuDetails = node.getChildNodes();
						Menu menu = new Menu();
						menu.setDate(date);

						for (int j = 0; j < menuDetails.getLength(); j++)
						{
							String name  = menuDetails.item(j).getNodeName();
							String value = menuDetails.item(j)
									.getTextContent()
									.replaceAll("\\(.+\\)( \\*)?$", "")
									.trim();

							if (name.equals("menu"))
								menu.setTitle(value);
							else if (name.equals("text"))
								menu.setName(value);
							else if (name.equals("speisentyp"))
								menu.setType(value);
							else if (name.equals("beilage"))
								menu.addSide(value);
						}

						if (!menu.getTitle().equals("Tages - Tipp"))
							list.add(menu);
					}
				}
			}
		}
		// if an error occurs, we can't do anything against it...
		catch (Exception e) { }

		return list;		
	}
}