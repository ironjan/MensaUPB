package de.najidev.mensaupb.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;

import de.najidev.mensaupb.Application;
import de.najidev.mensaupb.entity.Menu;

public class XmlParser
{
	final static String url  = "http://www.studentenwerk-pb.de/fileadmin/xml/mensa.xml";

	public static List<Menu> getMenus(int week)
	{
		Context context = Application.getContext();
		FileInputStream is = null;

		// open file - download it before, if necessary
		try
		{
			is = context.openFileInput(week + ".xml");
		}
		catch (FileNotFoundException e1)
		{
			downloadFile(week);
			try
			{
				is = context.openFileInput(week + ".xml");
			}
			catch (FileNotFoundException e2) { }
		}


		List<Menu> list = new ArrayList<Menu>();
		// parse file
		try
		{
			Document document = DocumentBuilderFactory
				.newInstance()
				.newDocumentBuilder()
				.parse(
					changeEncoding(is, "windows-1252", "utf-8")
				);
			
			NodeList dayList = document.getElementsByTagName("tag");

			for (int i = 0; i < dayList.getLength(); i++)
			{
				Date date = null;
				for (int k = 0; k < dayList.item(i).getChildNodes().getLength(); k++)
				{
					Node node = dayList.item(i).getChildNodes().item(k);
					if (node.getNodeName().equals("datum"))
						date = new SimpleDateFormat("dd.MM.yyyy").parse(node.getTextContent());
					else if (node.getNodeName().equals("menue"))
					{
						NodeList menuDetails = node.getChildNodes();
						Menu menu   = new Menu();
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
		// if an error occures, we can't do anything against it...
		catch (Exception e) { }


		return list;
	}

	protected static void downloadFile(int week)
	{
		try
		{
			Context context = Application.getContext();
	
			URL url  = new URL(XmlParser.url);
			url.openConnection();
	
			// Copy resource to local file, use remote file
			// if no local file name specified
			InputStream is = url.openStream();
			
			FileOutputStream fos= context.openFileOutput(week + ".xml", Context.MODE_PRIVATE);
	
			int oneChar;
			while ((oneChar=is.read()) != -1)
			{
				fos.write(oneChar);
			}
			is.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static InputSource changeEncoding(InputStream in, String from, String to) throws IOException
	{
		from = from.toLowerCase();
		to   = to.toLowerCase();

		BufferedReader br = new BufferedReader(new InputStreamReader(in, from));
		StringBuilder sb = new StringBuilder();

		String l = br.readLine().toLowerCase().replace(from, to);
		sb.append(l + "\n");
		while ((l = br.readLine()) != null)
			sb.append(l);

		br.close();
		return new InputSource(new StringReader(sb.toString()));
	}
}
