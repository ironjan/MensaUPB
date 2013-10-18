package de.najidev.mensaupb.helper;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import android.app.*;
import android.content.*;
import android.content.DialogInterface.OnCancelListener;
import android.os.*;
import android.util.*;
import de.najidev.mensaupb.activities.*;
import de.najidev.mensaupb.entity.*;

public class PrepareMenuRepositoryTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = PrepareMenuRepositoryTask.class
			.getSimpleName();
	MainActivity activity;
	Context applicationContext;
	MenuRepository menuRepository;
	ProgressDialog dialog;

	protected final String charsetOriginal = "windows-1252";
	protected final String charsetWanted = "utf-8";

	public PrepareMenuRepositoryTask(final MainActivity activity,
			final Context applicationContext,
			final MenuRepository menuRepository) {
		this.activity = activity;
		this.applicationContext = applicationContext;
		this.menuRepository = menuRepository;

		dialog = new ProgressDialog(activity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Download des Mensaplans...");

		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(final DialogInterface dialog) {
				activity.finish();
			}
		});
	}

	@Override
	protected void onPreExecute() {
		dialog.show();
	}

	@Override
	protected void onPostExecute(final Void result) {
		super.onPostExecute(result);

		activity.getDayPagerAdapter().notifyDataSetChanged();

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	protected Void doInBackground(final Void... params) {
		final List<Menu> menus = new ArrayList<Menu>();

		for (final String location : applicationContext.getAvailableLocations()
				.values()) {
			final String url = "http://www.studentenwerk-pb.de/fileadmin/xml/"
					+ location + ".xml";
			final InputSource downloadedXmlContent = downloadFile(url);
			final List<Menu> parsedMenus = parseXML(downloadedXmlContent);

			for (final Menu menu : parsedMenus) {
				menu.setLocation(location);
			}

			menus.addAll(parsedMenus);

		}

		// put results to repository
		menuRepository.persistMenus(menus);

		return null;
	}

	public InputSource downloadFile(final String url) {
		try {
			Log.v(TAG, "Starting download of " + url);

			final HttpClient httpclient = new DefaultHttpClient();
			final HttpResponse response = httpclient.execute(new HttpGet(url));

			final BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), charsetOriginal));
			final StringBuilder sb = new StringBuilder();

			String l = br.readLine().toLowerCase()
					.replace(charsetOriginal, charsetWanted);
			sb.append(l + "\n");
			while ((l = br.readLine()) != null) {
				sb.append(l);
			}

			br.close();
			Log.v(TAG, "Download of " + url + " complete");
			return new InputSource(new StringReader(sb.toString()));
		} catch (final Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}

	protected List<Menu> parseXML(final InputSource is) {
		Log.v(TAG, "Parsing an menu xml file.");
		final List<Menu> list = new ArrayList<Menu>();
		// parse file
		try {
			final Document document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(is);

			final NodeList dayList = document.getElementsByTagName("tag");

			for (int i = 0; i < dayList.getLength(); i++) {
				Date date = null;
				for (int k = 0; k < dayList.item(i).getChildNodes().getLength(); k++) {
					final Node node = dayList.item(i).getChildNodes().item(k);
					if (node.getNodeName().equals("datum")) {
						date = new SimpleDateFormat("dd.MM.yyyy").parse(node
								.getFirstChild().getNodeValue());
					}
					else if (node.getNodeName().equals("menue")) {
						final NodeList menuDetails = node.getChildNodes();
						final Menu menu = new Menu();
						menu.setDate(date);

						for (int j = 0; j < menuDetails.getLength(); j++) {
							final Node item = menuDetails.item(j);
							final String name = item.getNodeName();
							final Node firstChild = item.getFirstChild();

							if (firstChild != null) {
								final String nodeValue = firstChild
										.getNodeValue();
								final String value = nodeValue.replaceAll(
										"\\(.+\\)( \\*)?$", "").trim();

								if (name.equals("menu")) {
									menu.setTitle(value);
								}
								else if (name.equals("text")) {
									menu.setName(value);
								}
								else if (name.equals("speisentyp")) {
									menu.setType(value);
								}
								else if (name.equals("beilage")) {
									menu.addSide(value);
								}
							}
						}
						if (!menu.isTagesTipp()) {
							list.add(menu);
						}
					}
				}
			}
		} catch (final Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return list;
	}
}