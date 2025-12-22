package de.halva6.snackman.model.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Utility class to generate or load a game map.
 * <p>
 * Maps can either be loaded from a CSV file or generated randomly. Each map is
 * represented as a 2D integer array where each value represents a tile type.
 * </p>
 */
public class GenerateMap
{
	private final String filePath;
	private int[][] map;

	/**
	 * Creates a new map by loading data from a CSV file.
	 *
	 * @param width    the width of the map in tiles
	 * @param height   the height of the map in tiles
	 * @param filePath the path to the CSV file containing map data
	 */
	public GenerateMap(int width, int height, String filePath)
	{
		this.filePath = filePath;
		this.map = new int[width][height];
		initMap();
	}

	/**
	 * Creates a new map by generating it randomly.
	 * <p>
	 * Used if no specific map file is provided.
	 * </p>
	 *
	 * @param width  the width of the map in tiles
	 * @param height the height of the map in tiles
	 */
	public GenerateMap(int width, int height)
	{
		this.filePath = "";
		this.map = new int[width][height];
		generateRandomMap();
	}

	/**
	 * Initializes the map by reading values from a CSV file.
	 * <p>
	 * Each line in the CSV file corresponds to a row in the map, with values
	 * separated by commas.
	 * </p>
	 */
	private void initMap()
	{
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath))))
		{
			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
			{
				// Split the line by comma and convert to a List
				int[] values = strArrToIntArr(line.split(","));
				map[i] = values;
				i++;
			}
		} catch (IOException e)
		{
			System.err.println("Error reading the CSV file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Converts an array of strings to an array of integers.
	 *
	 * @param arr the array of strings to convert
	 * @return the resulting array of integers
	 */
	private int[] strArrToIntArr(String[] arr)
	{
		int[] intArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			intArr[i] = Integer.valueOf(arr[i]);
		}

		return intArr;
	}

	/**
	 * Prints the given matrix to the console.
	 * <p>
	 * Mainly used for debugging purposes.
	 * </p>
	 *
	 * @param matrix the 2D array to print
	 */
	@SuppressWarnings("unused")
	private void printMatrix(int[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			System.out.println(Arrays.toString(matrix[i]));
		}
	}

	/**
	 * Generates a random map.
	 */
	private void generateRandomMap()
	{
	}

	/**
	 * Sets wall tiles in the map.
	 */
	private void setWalls()
	{

	}

	public int[][] getMap()
	{
		return this.map;
	}
}
