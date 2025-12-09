package de.halva6.snackman.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GenerateMap
{
	private static final String FILE_PATH = "res/level/Level1.csv";
	private int[][] map;

	public GenerateMap(int width, int height)
	{
		this.map = new int[width][height];
		initMap();
	}

	private void initMap()
	{
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH)))
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

	private int[] strArrToIntArr(String[] arr)
	{
		int[] intArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			intArr[i] = Integer.valueOf(arr[i]);
		}

		return intArr;
	}

	@SuppressWarnings("unused")
	private void printMatrix(int[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			System.out.println(Arrays.toString(matrix[i]));
		}
	}

	public int[][] getMap()
	{
		return this.map;
	}
}
