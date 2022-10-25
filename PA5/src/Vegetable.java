/*
* AUTHOR: Kevin Nisterenko
* FILE: Vegetable.java
* ASSIGNMENT: Programming Assignment 5 - Garden
* COURSE: CSc 210; Fall 2021
* PURPOSE: This program defines the Vegetable sub class of the Plant super class and its
* methods to be used in PA5Main.java. It creates a Vegetable object by using the super class 
* constructor and it defines the growOnce method that overrides the plant object one. 
*
* USAGE: 
* java PA5Main
*
* There are no inputs for this specific file. 
*/

public class Vegetable extends Plant {
	
	/*
	 * Constructor for a Vegetable object, calls the superclass constructor with the given name and 
	 * updates the plot 2d array to have the initial vegetable representation. 
	 * 
	 * @param name, string that represents the name of the vegetable. 
	 */
	public Vegetable(String name) {
		super(name);
		// Put the initial character on the plot at the vegetable location (top of plot)
		this.plot[0][2] = this.initial; 
	}
	
	/*
	 * Method for the Vegetable subclass that uses the current size to check if growth is possible. 
	 * If it is, it grows the vegetable from the top down once by changing a specific character in the 
	 * plant's plot. 
	 */
	@Override
	public void growOnce() {
		if (this.currSize + 1 > MAX_SIZE) {
			return;
		} else {
			this.currSize++; // Update currSize
			this.plot[0 + this.currSize][2] = this.initial; //Update plot with char
		}
	}
	

}
