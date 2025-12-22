package de.halva6.snackman.model.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class responsible for loading and saving game levels and their
 * statistics.
 * <p>
 * Levels can be loaded from internal XML files or generated randomly. Level
 * progress, high scores, best times, and locked status are stored externally in
 * the user's home directory.
 * </p>
 */
public class LevelLoader
{
	/** The currently active level number. */
	public static int levelNumber = 1;

	/** Path to the internal XML file containing level definitions. */
	private final static String LEVEL_XML_PATH = "/level/leveldata.xml";
	/** Path to the external XML file storing user level statistics. */
	private static final String EXTERNAL_STATS_PATH = System.getProperty("user.home") + "/.snackman/levelstats.xml";

	/** Factory for parsing XML documents. */
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	/** Factory for transforming and saving XML documents. */
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

	/**
	 * Loads a level by its unique ID from the internal XML file.
	 *
	 * @param levelId the ID of the level to load
	 * @return a {@link LevelData} object representing the level, or {@code null} if
	 *         not found
	 */
	public static LevelData loadLevelById(int levelId)
	{
		levelNumber = levelId;
		try
		{
			// load XML
			InputStream is = LevelLoader.class.getResourceAsStream(LEVEL_XML_PATH);

			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();

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
					return parseLevel(levelElement, id);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static LevelData parseLevel(Element levelElement, int id)
	{

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

		int[] ownStats = LevelLoader.loadExternalLevelStats(id);

		LevelData ld = new LevelData(id, playerStartX, playerStartY, enemyStartX, enemyStartY, tilesPath, score, time,
				ownStats[0], ownStats[1]);

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

	/**
	 * Loads a random level with default player and enemy positions.
	 *
	 * @return a {@link LevelData} object representing a randomly generated level
	 */
	public static LevelData loadRandomLevel()
	{
		Random r = new Random();
		int enemies = r.nextInt(5) + 1; // max 5 enemies

		int[] enemyStartX = new int[enemies];
		int[] enemyStartY = new int[enemies];

		for (int i = 0; i < enemies; i++)
		{
			enemyStartX[i] = 12; // default enemy start position
			enemyStartY[i] = 11;
		}

		// the second and third argument is the default start position of the player
		return new LevelData(-1, 1, 1, enemyStartX, enemyStartY, "", 0, 0, 0, 0);
	}

	/**
	 * Saves external statistics for a level, such as highscore, best time, and
	 * locked status.
	 *
	 * @param levelId         the ID of the level
	 * @param highscore       the high score achieved
	 * @param bestTimeSeconds the best time in seconds
	 * @param locked          0 if unlocked, 1 if locked
	 */
	public static void saveExternalLevelStats(int levelId, int highscore, int bestTimeSeconds, int locked)
	{
		try
		{
			File file = new File(EXTERNAL_STATS_PATH);
			file.getParentFile().mkdirs();

			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
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
			setOrCreate(document, levelElement, "locked", String.valueOf(locked));

			removeWhitespaceNodes(document);

			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

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

	private static void removeWhitespaceNodes(Node node)
	{
		NodeList children = node.getChildNodes();

		for (int i = children.getLength() - 1; i >= 0; i--)
		{
			Node child = children.item(i);

			if (child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().isEmpty())
			{
				node.removeChild(child);
			} else if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				removeWhitespaceNodes(child);
			}
		}
	}

	/**
	 * Loads external statistics for a specific level.
	 *
	 * @param levelId the ID of the level
	 * @return an int array containing [0] = highscore, [1] = bestTimeSeconds, [2] =
	 *         locked status
	 */
	public static int[] loadExternalLevelStats(int levelId)
	{
		int[] result = new int[] { 0, 0, 0 }; // [0] = highscore, [1] = time

		try
		{
			File file = new File(EXTERNAL_STATS_PATH);
			if (!file.exists())
				return result;

			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
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
					result[2] = Integer.parseInt(levelElement.getElementsByTagName("locked").item(0).getTextContent());

					break;
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Loads external statistics for all levels.
	 *
	 * @param numberOfLevels the total number of levels
	 * @return a 2D array where each row corresponds to a level and columns are [0]
	 *         = highscore, [1] = bestTimeSeconds, [2] = locked status
	 */
	public static int[][] loadAllExternalLevelStats(int numberOfLevels)
	{
		int[][] result = new int[numberOfLevels][3];
		try
		{
			File file = new File(EXTERNAL_STATS_PATH);
			if (!file.exists())
				return result;

			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(file));
			document.getDocumentElement().normalize();

			NodeList levelNodes = document.getElementsByTagName("level");

			for (int i = 0; i < levelNodes.getLength(); i++)
			{
				Element levelElement = (Element) levelNodes.item(i);

				result[i][0] = Integer
						.parseInt(levelElement.getElementsByTagName("highscore").item(0).getTextContent());
				result[i][1] = Integer
						.parseInt(levelElement.getElementsByTagName("bestTimeSeconds").item(0).getTextContent());

				result[i][2] = Integer.parseInt(levelElement.getElementsByTagName("locked").item(0).getTextContent());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
}