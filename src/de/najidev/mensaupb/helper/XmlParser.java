package de.najidev.mensaupb.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import de.najidev.mensaupb.entity.Menu;

public class XmlParser
{
	public List<Menu> getMenus(InputSource is)
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
		// if an error occures, we can't do anything against it...
		catch (Exception e) { }

		return list;		
	}
}
