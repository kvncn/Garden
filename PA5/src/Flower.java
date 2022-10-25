/*
* AUTHOR: Kevin Nisterenko
* FILE: Flower.java
* ASSIGNMENT: Programming Assignment 5 - Garden
* COURSE: CSc 210; Fall 2021
* PURPOSE: This program defines the Flower sub class of the Plant super class and its
* methods to be used in PA5Main.java. It creates a Flower object by using the super class 
* constructor and it defines the growOnce method that overrides the plant object one. 
*
* USAGE: 
* java PA5Main
*
* There are no inputs for this specific file. 
*/

import java.util.*;

public class Flower extends Plant {
	// Instance Variable represents the filled coordinates on the flower's grid
	private List<int[]> filledCoords;
	
	/*
	 * Constructor for a Flower object, calls the superclass constructor with the 
	 * given name and updates the plot 2d array to have the initial flower representation.
	 * It also initializes the filledCoords List of integer arrays which is used in the 
	 * growth method.  
	 * 
	 * @param name, string that represents the name of the flower. 
	 */
	public Flower(String name) {
		super(name);
		// Put the initial character on the plot at the flower location (middle of plot)
		this.plot[2][2] = this.initial;
		
		// Initialize the filled coords list with the initial coordinate
		List<int[]> coords = new ArrayList<int[]>();
		this.filledCoords = coords;
		int[] initialCoords = {2,2};
		this.filledCoords.add(initialCoords);
		
	}
	
	/*
	 * Method for the Flower subclass that iterates over the list of filled coordinates in the 
	 * flower's plot and grows the flower once by updating the adjacent characters of the plot.  
	 */
	@Override
	public void growOnce() {
		int oldSize = this.filledCoords.size();
		// Iterates over the old coordinates
		for (int i = 0; i < oldSize; i++) {
			int[] coord = this.filledCoords.get(i);
			int cellRow = coord[0];
			int cellCol = coord[1];
			List<int[]> newCoords = new ArrayList<int[]>();
			// Checks if we can add to the column
			if (cellRow > 0 && cellRow < 4) {
				int[] adjUp = {cellRow-1, cellCol};
				int[] adjDown = {cellRow+1, cellCol};
				
				this.plot[cellRow-1][cellCol] = this.initial; //Update plot with char
				this.plot[cellRow+1][cellCol] = this.initial; //Update plot with char
				
				newCoords.add(adjUp);
				newCoords.add(adjDown);
			}
			// Checks if we can add to the row
			if (cellCol > 0 && cellCol < 4) {
				int[] adjLeft = {cellRow, cellCol-1};
				int[] adjRight = {cellRow, cellCol+1};
				
				this.plot[cellRow][cellCol-1] = this.initial; //Update plot with char
				this.plot[cellRow][cellCol+1] = this.initial; //Update plot with char
				
				newCoords.add(adjLeft);
				newCoords.add(adjRight);
			}
			// Updates the current filled coordinates
			this.filledCoords.addAll(newCoords);
		}
	}

}
