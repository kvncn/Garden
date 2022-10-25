/*
* AUTHOR: Kevin Nisterenko
* FILE: PA5Main.java
* ASSIGNMENT: Programming Assignment 5 - Garden
* COURSE: CSc 210; Fall 2021
* PURPOSE: This program reads in a text file from the command line 
* which contains information to initialize a Garden object and specifies 
* commands to run a simulation on the garden object and the plant objects 
* within it. The Garden consists of Plant objects, and there
* are 3 sub classes of Plants (Tree, Flower, Vegetable). Plants 
* can be grown, removed, or planted. The Garden can be printed.
*
* USAGE: 
* java PA5Main infile 
*
* where infile is the name of a file in the command line that has the
* following format
*
* ----------- EXAMPLE INPUT -------------
* Input file:
* -------------------------------------------
* | rows: 3
* | cols: 10
* |
* | PLANT (0, 0) banana
* | PRINT
* | GROW 2
* | GROW 4 (1, 3)
* | GROW 1 vegetable
* | GROW 1 lily
* | HARVEST
* | HARVEST (2, 3)
* | HARVEST garlic
* | PICK 
* | PICK (4, 3) 
* | PICK rose
* | CUT 
* | CUT (5, 2) 
* | CUT pine 
* -------------------------------------------
*
* The commands shown above are all of the commands that are supported
* by this program. It is assumed that (except for some specific errors defined 
* in the program specifications), the input follows these format rules and will 
* match the specifications.
*/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class PA5Main {

	public static void main(String[] args) {
		String fileName = args[0];
		
		Scanner file = readFile(fileName);
		
		Garden garden = initializeGarden(file);		
		
		readCommands(file, garden);
	}
	
	/*
	 * A helper method that reads the coordinates from a given string and stores them 
	 * in an integer array for easy access. 
	 * 
	 * @param coordinate, string that in the  "(row, col)" format which contains a given 
	 * coordinate.
	 * @return int[]{row,col}, integer array of two integers that represent row and column
	 * number in the given string
	 */
	public static int[] readCoords(String coordinate) {
		// Read the integers between the comma, remove parenthesis as well
		int row = Integer.parseInt(coordinate.split(",")[0].replace("(", ""));
		int col = Integer.parseInt(coordinate.split(",")[1].replace(")", "")); 
		return new int[] {row, col};
	}
	
	/*
	 * A helper method that uses a give string for file name and tries to open the file 
	 * and return the file object for use in other methods. 
	 * 
	 * @param fileName, string given in the command line that represents the name of the 
	 * input file.
	 * @return fileObj, scanner object that is the file object of the input file for 
	 * the program.
	 */
	public static Scanner readFile(String fileName) {
		Scanner fileObj = null;
		
		try {fileObj = new Scanner(new File(fileName));}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		return fileObj;
	}
	
	/*
	 * A helper method that reads the coordinates from the first two lines of the file to 
	 * create a Garden object of the given size. 
	 * 
	 * @param fileObj, scanner object that is the file object of the input file for 
	 * the program. 
	 * @return garden, garden object with empty plots, used in the later commands to 
	 * run the garden simulation. 
	 */
	public static Garden initializeGarden(Scanner fileObj) {
		//get the col and row and initialize the garden with these measurements
		int row = Integer.parseInt(fileObj.nextLine().split(" ")[1]);
		int col = Integer.parseInt(fileObj.nextLine().split(" ")[1]);
		
		if (col > 16) {
			System.out.println("Too many plot columns.");
			System.exit(0);
		}
		
		Garden garden = new Garden(row, col);
		
		return garden;
	}
	
	/*
	 * A helper method that continuously reads the commands from the input file and 
	 * calls the appropriate method from the garden object or helper from Main to perform 
	 * the command. 
	 * 
	 * @param fileObj, scanner object that is the file object of the input file for 
	 * the program. 
	 * @param garden, garden object in which the simulation is run. 
	 */
	public static void readCommands(Scanner fileObj, Garden garden) {
		while (fileObj.hasNext()) {
			String[] line = fileObj.nextLine().toLowerCase().split(" "); 
			// Plant command
			if (line[0].equals("plant")) {
				int[] coords = readCoords(line[1]);
				garden.plantPlot(line[2], coords[0], coords[1]);
			// Print command
			} else if (line[0].equals("print")) {
				System.out.println("> PRINT");
				System.out.println(garden);
			// Checks for the grow command
			} else if (line[0].equals("grow")) {
				growCommands(line, garden);
			// Checks for the remove commands (one for each subclass of Plant)
			} else if (line[0].equals("harvest")) {
				removeCommands(line, garden, "vegetable");
			} else if (line[0].equals("pick")) {
				removeCommands(line, garden, "flower");
			} else if (line[0].equals("cut")) {
				removeCommands(line, garden, "tree");
			}
		}
	}
	
	/*
	 * A helper method that parses a given string array to call the appropriate garden growth 
	 * methods from the garden object. 
	 * 
	 * @param line, string array that contains the growth amount information, the grow command 
	 * itself and the specifications for the growth (plot or type of plant)
	 * @param garden, garden object in which the simulation is run. 
	 */
	public static void growCommands(String[] line, Garden garden) {
		int growthAmount = Integer.parseInt(line[1]);
		// Checks the specifications of the command to either grow all, a specific plot 
		// plant subclass or plant type 
		if (line.length == 2) { 
			System.out.println("> GROW " + growthAmount + "\n");
			garden.growGarden(growthAmount, "");
		} else {
			System.out.println("> GROW " + growthAmount + " " + line[2] + "\n");
			if (line[2].startsWith("(")){
				int[] plotCoords = readCoords(line[2]);
				garden.growPlot(growthAmount, plotCoords[0], plotCoords[1]);
			} else {
				garden.growGarden(growthAmount, line[2]);
			}
		}
	}
	
	/*
	 * A helper method that parses a given string array to call the appropriate garden remove
	 * methods from the garden object. 
	 * 
	 * @param line, string array that contains the remove command information (type of command, plant
	 * plot)
	 * @param garden, garden object in which the simulation is run. 
	 * @param plantType, string that represents which subclass of Plant the remove command refers to.
	 */
	public static void removeCommands(String[] line, Garden garden, String plantType) {
		String printRep = "";
		// Concatenates the type of removal depending on the subclass of plant
		if (plantType.equals("vegetable")) {
			printRep += "> HARVEST";
		} else if (plantType.equals("flower")) {
			printRep += "> PICK";
		} else if (plantType.equals("tree")) {
			printRep += "> CUT";
		}
		
		// Checks the specifications of the command to either remove all, a specific plot 
		// or plant type
		if (line.length == 1) {
			System.out.println(printRep + "\n");
			garden.removeGarden(plantType);
		} else if (line[1].startsWith("(")) {
			int[] plotCoords = readCoords(line[1]);
			printRep += " " + line[1];
			System.out.println(printRep + "\n");
			garden.removePlot(plotCoords[0], plotCoords[1], plantType);
		} else {
			printRep += " " + line[1];
			System.out.println(printRep + "\n");
			if (garden.matchType(line[1], plantType)) {
				garden.removeGarden(line[1]);
			}
		}
	}
}
