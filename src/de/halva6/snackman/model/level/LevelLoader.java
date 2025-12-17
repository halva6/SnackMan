package de.halva6.snackman.model.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LevelLoader
{
	public static int levelNumber = 1;

	private final static String LEVEL_XML_PATH = "/level/leveldata.xml";
	private static final String EXTERNAL_STATS_PATH = System.getProperty("user.home") + "/.snackman/levelstats.xml";

	public static LevelData loadLevelById(int levelId)
	{
		levelNumber = levelId;
		try
		{
			// load XML
			InputStream is = LevelLoader.class.getResourceAsStream(LEVEL_XML_PATH);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(is);
			document.getDocumentElement().normalize();

			// all level elements
			NodeList levelNodes = document.getElementsByTagName("level");

			// try to find the correct level
			for (int i = 0; i < levelNodes.getLength(); i++)
			{

				Element levelElement = (Element) levelNodes.item(i);

				int id = Integer.parseInt(levelElement.getElementsByTagName("levelId").item(0).getTextContent().trim());

				if (levelId == id)
				{
					return parseLevel(levelElement);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static LevelData parseLevel(Element levelElement)
	{
		int levelId = getInt(levelElement, "levelId");

		Element playerStart = (Element) levelElement.getElementsByTagName("playerStartPosition").item(0);

		int playerStartX = getInt(playerStart, "x");
		int playerStartY = getInt(playerStart, "y");

		NodeList enemyNodes = levelElement.getElementsByTagName("enemy");

		int[] enemyStartX = new int[enemyNodes.getLength()];
		int[] enemyStartY = new int[enemyNodes.getLength()];

		for (int i = 0; i < enemyNodes.getLength(); i++)
		{
			Element enemyElement = (Element) enemyNodes.item(i);

			enemyStartX[i] = getInt(enemyElement, "x");
			enemyStartY[i] = getInt(enemyElement, "y");

		}

		String tilesPath = getText(levelElement, "tiles");

		int score = getInt(levelElement, "score");
		int time = getInt(levelElement, "bestTimeSeconds");

		LevelData ld = new LevelData(levelId, playerStartX, playerStartY, enemyStartX, enemyStartY, tilesPath, score,
				time);

		return ld;
	}

	private static int getInt(Element parent, String tag)
	{
		return Integer.parseInt(getText(parent, tag));
	}
	
	private static String getText(Element parent, String tag)
	{
		return parent.getElementsByTagName(tag).item(0).getTextContent().trim();
	}

	public static void saveExternalLevelStats(int levelId, int highscore, int bestTimeSeconds)
	{
		try
		{
			File file = new File(EXTERNAL_STATS_PATH);
			file.getParentFile().mkdirs();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document;

			if (file.exists())
			{
				document = builder.parse(new FileInputStream(file));
			} else
			{
				document = builder.newDocument();
				Element root = document.createElement("levels");
				document.appendChild(root);
			}

			Element root = document.getDocumentElement();
			NodeList levelNodes = root.getElementsByTagName("level");

			Element levelElement = null;

			for (int i = 0; i < levelNodes.getLength(); i++)
			{
				Element el = (Element) levelNodes.item(i);
				int id = Integer.parseInt(el.getAttribute("id"));

				if (id == levelId)
				{
					levelElement = el;
					break;
				}
			}

			if (levelElement == null)
			{
				levelElement = document.createElement("level");
				levelElement.setAttribute("id", String.valueOf(levelId));
				root.appendChild(levelElement);
			}

			setOrCreate(document, levelElement, "highscore", String.valueOf(highscore));
			setOrCreate(document, levelElement, "bestTimeSeconds", String.valueOf(bestTimeSeconds));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void setOrCreate(Document doc, Element parent, String tag, String value)
	{
		NodeList nodes = parent.getElementsByTagName(tag);

		Element element;
		if (nodes.getLength() > 0)
		{
			element = (Element) nodes.item(0);
		} else
		{
			element = doc.createElement(tag);
			parent.appendChild(element);
		}

		element.setTextContent(value);
	}

	public static int[] loadExternalLevelStats(int levelId)
	{
		int[] result = new int[] { 0, 0 }; // [0] = highscore, [1] = time

		try
		{
			File file = new File(EXTERNAL_STATS_PATH);
			if (!file.exists())
				return result;

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(file));
			document.getDocumentElement().normalize();

			NodeList levelNodes = document.getElementsByTagName("level");

			for (int i = 0; i < levelNodes.getLength(); i++)
			{
				Element levelElement = (Element) levelNodes.item(i);
				int id = Integer.parseInt(levelElement.getAttribute("id"));

				if (id == levelId)
				{
					result[0] = Integer
							.parseInt(levelElement.getElementsByTagName("highscore").item(0).getTextContent());
					result[1] = Integer
							.parseInt(levelElement.getElementsByTagName("bestTimeSeconds").item(0).getTextContent());
					break;
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

}
