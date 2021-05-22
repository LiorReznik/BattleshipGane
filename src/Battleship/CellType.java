package Battleship;

/**
 * An enum that represents the type of the cell there are 4 options
 * FOG,HIT,MISS,SHIP
 */
enum CellType {
	FOG('~'),
	HIT('X'),
	MISS('M'),
	SHIP('O');
	
	private final char symbol;
	
	CellType(char symbol) {
		this.symbol = symbol;
	}
	
	char getSymbol() {
		return this.symbol;
	}
}
