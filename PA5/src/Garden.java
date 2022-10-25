/*
* AUTHOR: Kevin Nisterenko
* FILE: Garden.java
* ASSIGNMENT: Programming Assignment 5 - Garden
* COURSE: CSc 210; Fall 2021
* PURPOSE: This program defines the Garden class and its methods to be used 
* in PA5Main.java. It stores plant objects as a 2d List and has methods to 
* print itself, plant a specific plant, grow plants and remove plants depending 
* on the specifications of user input in main. 
*
* USAGE: 
* java PA5Main
*
* There are no inputs for this specific file. 
*/

import java.util.*;

public class Garden {
	// Store the types of plants
	private static final Set<String> TREE_TYPES = 
			new HashSet<String>(Arrays.asList("oak", "willow", "banana", "coconut", "pine"));
	private static final Set<String> FLOWER_TYPES = 
			new HashSet<String>(Arrays.asList("iris", "lily", "rose", "daisy", "tulip", "sunflower"));
	private static final Set<String> VEGETABLE_TYPES =
			new HashSet<String>(Arrays.asList("garlic", "yam", "zucchini", "tomato", "lettuce"));
	
	// Instance Variables
	private List<List<Plant>> gardenPlots;
	private int row;
	private int col;
	
	/*
	 * Constructor for a Garden object, takes the row and column numbers and calls the 
	 * createGardenList method to initialize a 2d list of the appropriate size to store 
	 * the plant objects. 
	 * 
	 * @param row, integer that represents how many rows of plots the garden has.
	 * @param col, integer that represent how many columns of plots the garden has.
	 */
	public Garden(int row, int col) {
		this.row = row;
		this.col = col;
		this.gardenPlots = createGardenList();
	}
	
	/*
	 * Helper method for the garden object that uses the row and column attributes 
	 * to create the 2d list of plant objects of the appropriate size, in a sense, 
	 * the garden object itself.  
	 */
	private List<List<Plant>> createGardenList() {
		List<List<Plant>> gardenList = new ArrayList<List<Plant>>(this.row);
		
		// Iterates over the columns and creates a inner array for each one
		for (int i = 0; i < this.row; i++) {
			gardenList.add(new ArrayList<Plant>());
		}
		
		// Populates the initial garden with empty plots/plants
		for (int r = 0; r < this.row; r++) {
			List<Plant> rowList = gardenList.get(r);
			for (int c = 0; c < this.col; c++) {
				rowList.add(new Plant());
			}
		}
		return gardenList;
	}
	
	/*
	 * Method of the Garden object, takes a given plant name, row and column and 
	 * creates and plant the specified plant into the specified plot of the garden.
	 * 
	 * @param plantName, string that represents the name of a plant to be planted. 
	 * @param row, integer that represents the row of a specific plot in the garden.
	 * @param col, integer that represent the column of a specific plot in the garden.
	 */
	public void plantPlot(String plantName, int row, int col) {
		// Check for valid row and column to plant
		if (row > this.row - 1 || col > this.col - 1) {
			return;
		}
		// Can't plant where there is already a plant
		if (!this.gardenPlots.get(row).get(col).getName().equals("")) {
			return;
		}
		Plant plant = new Plant();
		// Checks the plant name to the plant types set 
		// to decide which subclass of Plant object to create
		if (TREE_TYPES.contains(plantName)) {
			plant = new Tree(plantName);
		} else if (FLOWER_TYPES.contains(plantName)) {
			plant = new Flower(plantName);
		} else if (VEGETABLE_TYPES.contains(plantName)) {
			plant = new Vegetable(plantName);
		}
		// Sets the created plant into the specified plot of the garden
		this.gardenPlots.get(row).set(col, plant); 
	}
	
	/*
	 * Method of the Garden object, takes a given growth amount and plot coordinates 
	 * to call the growth function of the plant in the specific plot. There are checks 
	 * for invalid plots.
	 * 
	 * @param amount, integer that represents the amount to grow a specific plant. 
	 * @param row, integer that represents the row of a specific plot in the garden.
	 * @param col, integer that represent the column of a specific plot in the garden.
	 */
	public void growPlot(int amount, int row, int col) {
		if (row > this.row-1) {
			System.out.println("Can't grow there.\n");
			return;
		}
		if (col > this.col-1) {
			System.out.println("Can't grow there.\n");
			return;
		}
		if (this.gardenPlots.get(row).get(col).getName().equals("")) {
			System.out.println("Can't grow there.\n");
			return;
		} else {
			this.gardenPlots.get(row).get(col).grow(amount);
		}
	}
	
	/*
	 * Method of the Garden object, takes a given growth amount and plant type 
	 * string and iterates over the garden object using nested loops to grow 
	 * all plants that follow the specifications provided by the parameters. 
	 * 
	 * @param amount, integer that represents the amount to grow a specific plant. 
	 * @param plant, string that represents a plant type/name or a plant subclass. 
	 */
	public void growGarden(int amount, String plant) {
		// Iterates over all plants and calls the growPlot method depending on the param
		for (int r = 0; r < this.row; r++) {
			for (int c = 0; c < this.col; c++) {
				Plant currPlant = this.gardenPlots.get(r).get(c);
				// Checks if the given string is empty (grow all), matches the plant class or matches 
				// the plant name/type
				if (currPlant.getClass().getName().toLowerCase().equals(plant) || currPlant.getName().equals(plant) || 
						plant.equals("")) {
					currPlant.grow(amount);
				} 
			}
		}
	}
	
	/*
	 * Method of the Garden object, takes given plot coordinates and plantClass
	 * to remove the specified plant from the garden. There are checks to check 
	 * if the removal is not possible, if the removal is possible, the plant is 
	 * removed from the garden and an empty plot initialized in its place.
	 *  
	 * @param row, integer that represents the row of a specific plot in the garden.
	 * @param col, integer that represent the column of a specific plot in the garden.
	 * @param plantClass, string that represents the name of a Plant subclass. 
	 */
	public void removePlot(int row, int col, String plantClass) {
		Plant currPlant = gardenPlots.get(row).get(col);
		if (!currPlant.getClass().getName().toLowerCase().equals(plantClass) || row > this.row-1 || col > this.col-1) {
			if (plantClass.equals("tree")) {
				System.out.println("Can't cut there.\n");
			} else if (plantClass.equals("vegetable")) {
				System.out.println("Can't harvest there.\n");
			}else if (plantClass.equals("flower")) {
				System.out.println("Can't pick there.\n");
			}
		} else {
			this.gardenPlots.get(row).set(col, new Plant());
		}
	}
	
	/*
	 * Method of the Garden object, takes a given plant type and plant subclass and checks if 
	 * it is a valid assignment.
	 * 
	 * @param plantType, string that represents a plant type/name.
	 * @param plantType, string that represents a plant subclass.
	 * @return boolean true if the plantType matches with the given subclass and false otherwise.
	 */
	public boolean matchType(String plantType, String plantClass) {
		if (plantClass.equals("vegetable") && VEGETABLE_TYPES.contains(plantType)) {
			return true;
		} else if (plantClass.equals("flower") && FLOWER_TYPES.contains(plantType)) {
			return true;
		} else if (plantClass.equals("tree") && TREE_TYPES.contains(plantType)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Method of the Garden object, takes a given plant type string and iterates 
	 * over the garden object using nested loops to remove all plants that
	 * follow the specifications provided by the parameter.  
	 * 
	 * @param plantType, string that represents a plant type/name.
	 */
	public void removeGarden(String plant) {
		// Iterates over all plants and calls the growPlot method depending on the param
		for (int r = 0; r < this.row; r++) {
			for (int c = 0; c < this.col; c++) {
				Plant currPlant = this.gardenPlots.get(r).get(c);
				// Checks if the given string is empty (grow all), matches the plant class or matches 
				// the plant name/type
				if (currPlant.getClass().getName().toLowerCase().equals(plant) || currPlant.getName().equals(plant)) {
					this.gardenPlots.get(r).set(c, new Plant());
				} 
			}
		}
	}
	
	/*
	 * Method of the Garden object, uses nested loops to appropriately concatenate each plot as a string 
	 * so that the garden object can be represented with all its plots and plants as a string.  
	 * 
	 * @return strRep, string representation of the garden object and all plots and plants within it. 
	 */
	public String toString() {
		String strRep = "";
		// Iterates over the inner rows list of the garden list
		for (int r = 0; r < this.row; r++) {
			// Iterates 5 times so that each plot can have 5 inner rows
			for (int i = 0; i < 5; i++) {
				// Iterates over each plant object of the inner rows
				for (int c = 0; c < this.col; c++) {
					Plant currPlant = this.gardenPlots.get(r).get(c);
					char[][] currPlot = currPlant.getPlot();
					// Iterates 5 times so that each plot can have 5 inner columns
					for (int j = 0; j < 5; j++) {
						strRep += currPlot[i][j];
					}
				}
				strRep += "\n";
			}
		}
		return strRep;
	}
}
