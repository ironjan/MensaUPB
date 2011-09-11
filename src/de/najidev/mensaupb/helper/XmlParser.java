package de.najidev.mensaupb.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

import de.najidev.mensaupb.Application;
import de.najidev.mensaupb.entity.Menu;

public class XmlParser
{
	final static String url  = "http://www.studentenwerk-pb.de/fileadmin/xml/mensa.xml";
	final static String file = "";

	public static List<Menu> getMenus(int kw)
	{
		Context context = Application.getContext();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		FileInputStream is = null;

		try
		{
			is = context.openFileInput("mensa.xml");
		}
		catch (FileNotFoundException e1)
		{
			downloadFile();
			try
			{
				is = context.openFileInput("mensa.xml");
			}
			catch (FileNotFoundException e2) { }
		}

		try
		{
			try
			{
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse((InputStream) is);
				NodeList ndList = document.getElementsByTagName("tag");

				for (int i = 0; i < ndList.getLength(); i++)
				{
					Log.w("xml", ndList.item(i).getNodeName() + " ");
					NodeList menuList = ndList.item(i).getChildNodes();

					for (int k = 0; k < menuList.getLength(); k++)
					{
						if ("menue" == menuList.item(k).getNodeName())
						{
							
						}
					}
				}
			}
			catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Menu> list = new ArrayList<Menu>();

		return list;
	}

	protected static void downloadFile()
	{
		try
		{
			Context context = Application.getContext();
	
			URL url  = new URL(XmlParser.url);
			URLConnection urlC = url.openConnection();
	
			// Copy resource to local file, use remote file
			// if no local file name specified
			InputStream is = url.openStream();
			
			FileOutputStream fos= context.openFileOutput("mensa.xml", Context.MODE_PRIVATE);
	
			int oneChar, count=0;
			while ((oneChar=is.read()) != -1)
			{
				fos.write(oneChar);
				count++;
			}
			is.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
