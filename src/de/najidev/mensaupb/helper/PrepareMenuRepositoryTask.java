package de.najidev.mensaupb.helper;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.slf4j.*;
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

	private static final SimpleDateFormat XML_DATE_FORMAT = new SimpleDateFormat(
			"dd.MM.yyyy");
	private static final String TAG = PrepareMenuRepositoryTask.class
			.getSimpleName();
	MainActivity activity;
	Context applicationContext;
	MenuRepository menuRepository;
	ProgressDialog dialog;

	protected final String charsetOriginal = "windows-1252";
	protected final String charsetWanted = "utf-8";

	private static final Logger LOGGER = LoggerFactory.getLogger(TAG);

	public PrepareMenuRepositoryTask(final MainActivity activity,
			final Context applicationContext,
			final MenuRepository menuRepository) {
		Object[] params = { activity, applicationContext, menuRepository };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PrepareMenuRepositoryTask({},{},{})",
					Arrays.deepToString(params));
		}

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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Constructed: PrepareMenuRepositoryTask({},{},{})",
					Arrays.deepToString(params));
		}
	}

	@Override
	protected void onPreExecute() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onPreExecute()");
		}
		dialog.show();
	}

	@Override
	protected void onPostExecute(final Void result) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onPostExecute({})", result);
		}
		super.onPostExecute(result);

		activity.getDayPagerAdapter().notifyDataSetChanged();

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onPostExecute({}) -> VOID", result);
		}
	}

	@Override
	protected Void doInBackground(final Void... params) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("doInBackground({})", Arrays.deepToString(params));
		}

		final List<Menu> menus = new ArrayList<Menu>();

		for (final String location : applicationContext.getAvailableLocations()
				.values()) {
			LOGGER.info("Starting location \"{}\"", location);

			final String url = "http://www.studentenwerk-pb.de/fileadmin/xml/"
					+ location + ".xml";
			final InputSource downloadedXmlContent = downloadFile(url);
			final List<Menu> parsedMenus = parseXML(downloadedXmlContent);

			for (final Menu menu : parsedMenus) {
				menu.setLocation(location);
			}

			menus.addAll(parsedMenus);
			LOGGER.info("Finished location \"{}\"", location);
		}

		menuRepository.persistMenus(menus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("doInBackground({}) -> VOID",
					Arrays.deepToString(params));
		}
		return null;
	}

	public InputSource downloadFile(final String url) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("downloadFile({})", url);
		}
		try {

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

			InputSource inputSource = new InputSource(new StringReader(
					sb.toString()));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("downloadFile({}) -> {}", url, inputSource);
			}
			return inputSource;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	protected List<Menu> parseXML(final InputSource is) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("parseXML({})", is);
		}
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

					String nodeName = node.getNodeName();

					if (nodeName.equals("datum")) {
						String dateNodeValue = node.getFirstChild()
								.getNodeValue();
						date = XML_DATE_FORMAT.parse(dateNodeValue);
					}
					else if (nodeName.equals("menue")) {
						processMenu(list, date, node);
					}
				}
			}
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("parseXML({}) -> list", is, list);
		}
		return list;
	}

	private static void processMenu(final List<Menu> list, Date date,
			final Node node) {
		Object[] params = { list, date, node };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processMenu({},{},{})", params);
			final Menu menu = new Menu();
			menu.setDate(date);

			final NodeList menuDetails = node.getChildNodes();
			for (int j = 0; j < menuDetails.getLength(); j++) {
				final Node item = menuDetails.item(j);
				processMenuDetail(menu, item);
			}

			if (!menu.isTagesTipp()) {
				list.add(menu);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("processMenu({},{},{}) -> VOID", params);
			}
		}
	}

	private static void processMenuDetail(final Menu menu, final Node item) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processMenuDetail({},{})", menu, item);
		}
		final String name = item.getNodeName();
		final Node firstChild = item.getFirstChild();
		if (null == firstChild) {
			return;
		}

		final String nodeValue = firstChild.getNodeValue();
		final String value = nodeValue.replaceAll("\\(.+\\)( \\*)?$", "")
				.trim();

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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processMenuDetail({},{}) -> VOID", menu, item);
		}
	}

	@Override
	public String toString() {
		return "PrepareMenuRepositoryTask [activity=" + activity
				+ ", applicationContext=" + applicationContext
				+ ", menuRepository=" + menuRepository + ", dialog=" + dialog
				+ ", charsetOriginal=" + charsetOriginal + ", charsetWanted="
				+ charsetWanted + "]";
	}
}