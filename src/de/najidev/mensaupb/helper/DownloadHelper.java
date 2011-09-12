package de.najidev.mensaupb.helper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadHelper
{
	public static void downloadFile(URL url, FileOutputStream fos)
	{
		try
		{
			url.openConnection();
			InputStream is = url.openStream();
	
			int oneChar;
			while ((oneChar=is.read()) != -1)
			{
				fos.write(oneChar);
			}
			is.close();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
