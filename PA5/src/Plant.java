/*
* AUTHOR: Kevin Nisterenko
* FILE: Plant.java
* ASSIGNMENT: Programming Assignment 5 - Garden
* COURSE: CSc 210; Fall 2021
* PURPOSE: This program defines the Plant super class and its methods to be used 
* in PA5Main.java. It creates a plant object, which is a 2d array of characters and 
* defines methods to create this 2d array (the plot) and also abstract methods to be 
* overriden by the plant subclasses.  
*
* USAGE: 
* java PA5Main
*
* There are no inputs for this specific file. 
*/

public class Plant {
	// Stores the maximum size a plant can reach
	protected static final int MAX_SIZE = 4;
	
	//Instance Variables 
	protected char[][] plot;
	protected String name;
	protected char initial;
	protected int currSize;
	
	/*
	 * Constructor for an empty Plant object, the name attribute is an empty string, 
	 * the initial is a period (which represents nothing in the plot) and the createPlotGrid 
	 * helper method is called to initialize the plant plot. 
	 */
	public Plant() {
		this.name = "";
		this.initial = '.';
		this.plot = createPlotGrid();
	}
	
	/*
	 * Constructor for a named Plant object, the name attribute is the given name, 
	 * the initial is the first character of the given name and the createPlotGrid 
	 * helper method is called to initialize the plant plot. 
	 * 
	 * @param name, string that represents the name of the plant. 
	 */
	public Plant(String name) {
		this.plot = createPlotGrid();
		this.name = name;
		this.initial = name.charAt(0);
		this.currSize = 0;
	}
	
	/*
	 * Helper method for the plant object that creates the 2d character array of 5 cells 
	 * in each direction that represent the plant plot, in a sense, the plant object itself. 
	 */
	private char[][] createPlotGrid() {
		char[][] plot = new char[5][5];
		// Iterates over the 2d array and puts periods as the character
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				plot[i][j] = '.';
			}
		}
		return plot;
	}
	
	/*
	 * Getter method for the plant object that returns the 2d character array that represents 
	 * the plant and plot. 
	 * 
	 * @return plot, 2d character array that represents the plant object.
	 */
	public char[][] getPlot() {
		return this.plot;
	}
	
	/*
	 * Getter method for the plant object that returns the plant's name. 
	 * 
	 * @return name, string that represents a plant's name. Empty string if the 
	 * plot is also empty. 
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * Method for the plant object that returns the plant's name that uses the given 
	 * growth amount to call the growOnce method and grow the plant the specified amount. 
	 * 
	 * @param amount, integer that represents the amount of growth for the plant. 
	 */
	public void grow(int amount) {
		// Runs grow once by the set amount of time
		for (int i = 0; i < amount; i++) {
			growOnce();
		}
	}
	
	/*
	 * Abstract method for the plant object that is overridden by the subclasses. It is used to make 
	 * the plant object grow once. 
	 */
	public void growOnce() {
		if (!this.name.equals(null)) {
			this.currSize++;
		}
	}
}
