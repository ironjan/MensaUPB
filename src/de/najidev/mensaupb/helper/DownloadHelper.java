package de.najidev.mensaupb.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;

public class DownloadHelper
{
	protected String charsetOriginal = "windows-1252";
	protected String charsetWanted   = "utf-8";

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
}
