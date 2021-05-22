package Battleship;

/**
 * A class that represents a 10X10 game board
 */
class Board {
	private final CellType[][] board;
	final int size;
	private boolean hidden;

	Board() {
		this.size = 10;
		this.hidden = false;
		this.board = new CellType[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.board[i][j] = CellType.FOG;
			}
		}
	}
	
	CellType getCell(int[] coordinates) {
		return this.getCell(coordinates[0], coordinates[1]);
	}
	
	CellType getCell(int xCoordinate, int yCoordinate) {
		if (xCoordinate < 0 || xCoordinate >= this.size || yCoordinate < 0 || yCoordinate >= this.size ) {
			throw new ArrayIndexOutOfBoundsException("the coordinates are out of bound for the board (10X10)");
		}
		return this.board[xCoordinate][yCoordinate];
	}
	
	void setCell(int[] coordinates, CellType type) {
		int xCoordinate = coordinates[0];
		int yCoordinate = coordinates[1];
		if (xCoordinate < 0 || xCoordinate >= this.size || yCoordinate < 0 || yCoordinate >= this.size ){
			throw new ArrayIndexOutOfBoundsException("the coordinates are out of bound for the board (10X10)");
		}
		this.board[xCoordinate][yCoordinate] = type;
	}
	/**
	 * A method that changes the state of the board for  hidden.
	 * when board is  hidden, the toString method makes a string that does not reveal the location
	 * of the ships.
	 */
	void hideBoard() {
		this.hidden = true;
	}
	/**
	 * A method that changes the state of the board for not hidden.
	 * when board is not hidden, the toString method makes a string that shows
	 * the board with the ships.
	 */
	void revealBoard() {
		this.hidden = false;
	}
	@Override
	public String toString() {
		StringBuilder board = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
		char rowChar = 'A';
		for (CellType[] row : this.board) {
			board.append(rowChar);
			board.append(' ');
			for (CellType cell : row) {
				board.append(this.hidden && cell == CellType.SHIP ? CellType.FOG.getSymbol() : cell.getSymbol());
				board.append(' ');
			}
			rowChar++;
			board.append('\n');
		}
		return board.toString();
	}
	/**
	 * a method to place ship on the board
	 * this is a helper method for the public placeOnBoard method.
	 * @param coordinates array of coordinates for ship placement [xStart,xEnd,yStart,yEnd]
	 * @param direction the direction of the ship (vertical or horizontal)
	 */
	private void placeOnBoard(int[] coordinates, Direction direction) {
		int startI = coordinates[0];
		int endI = coordinates[1];
		int startJ = coordinates[2];
		int endJ = coordinates[3];
		if (direction == Direction.HORIZONTAL) {
			for (int j = startJ; j < endJ; j++) {
				this.board[startI][j] = CellType.SHIP;
			}
		} else {
			for (int i = startI; i < endI; i++) {
				this.board[i][startJ] = CellType.SHIP;
			}
		}
		
		
	}
	/**
	 * A method that checks if the desired cell is free,
	 * this method is a helper method for public placeOnBoard
	 * @param coordinates
	 * @return
	 */
	private boolean isFree(int[] coordinates) {
		int startI = coordinates[0] == 0 ? 0 : coordinates[0] - 1;
		int endI = coordinates[1] == this.size ? this.size : coordinates[1] + 1;
		int startJ = coordinates[2] == 0 ? 0 : coordinates[2] - 1;
		int endJ = coordinates[3] == this.size ? this.size : coordinates[3] + 1;
		
		for (int i = startI; i < endI; i++) {
			for (int j = startJ; j < endJ; j++) {
				if (this.board[i][j] != CellType.FOG) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Method to place the ship on the board
	 * the method gets integer array of coordinates [X_start , X_end, Y_start, Y_end]
	 * the method checks if the desired place valid if so, it calls the placeObBoard helper method to place the ship.
	 * @param coordinates array of coordinates [X_start , X_end, Y_start, Y_end]
	 * @return boolean indicator of success
	 */
	boolean placeOnBoard(int[] coordinates) {
		boolean result = false;
		if (coordinates == null || coordinates.length < 4) {
			System.out.println("Error! You entered the wrong coordinates! Try again:");
		} else {
			Direction direction = PointUtility.isVertical(coordinates);
			if (direction == Direction.NONE) {
				System.out.println("Error! Wrong ship location! Try again:\n");
			} else {
				PointUtility.swap(coordinates);
			//	int[] coordinates = PointUtility.convertCoordinatesToInts(start, end);
				if (!isFree(coordinates)) {
					System.out.println("Error! You placed it too close to another one. Try again:");
				} else {
					placeOnBoard(coordinates, direction);
					result = true;
				}
			}
		}
		return result;
	}
	/**
	 * A method that makes a shoot based on the provided coordinates
	 * @param stringCoordinates coordinated of the board in string format when charAt(0) is coordinate for x and charAt(1) for y
	 * @return String that indicates if the shoot was successful or not  (hit, miss or wrong coordinates)
	 */
	 String makeAShoot(String stringCoordinates) {
		String result = null;
		boolean finished = false;
		if (stringCoordinates == null) {
			result = "Error! You entered the wrong coordinates!";
		} else {
			int[] coordinates = PointUtility.convertCoordinatesToInts(stringCoordinates);
			if (this.getCell(coordinates) == CellType.SHIP) {
				this.setCell(coordinates,CellType.HIT);
				if (this.isSunk(coordinates)) {
					result =
							this.isWon() ? "You sank the last ship. You won. Congratulations!" : "You sank a ship!";
				} else {
					result = "You hit a ship!";
				}
				finished = true;
			}
			if (!finished) {
				if (this.getCell(coordinates) == CellType.FOG) {
					this.setCell(coordinates,CellType.MISS);
					result = "You missed.";
				}
			}
		}
		return result;
	}
	/**
	 * hellper method to check if all the ships has sunken and the game is over.
	 * @return true if all the ships has sunken or false otherwise.
	 */
	private boolean isWon() {
		for (int i = 0;i < this.size; i++){
			for (int j = 0; j < this.size; j++) {
				if (this.getCell(i,j) == CellType.SHIP) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * a helper method to check if the ship has sunken horizontally based on the last hit coordinate
	 * @param coordinates the last hit coordinates [X_coordinate, Y_coordinate].
	 * @return boolean indicator true if the ship has sunken or false otherwise
	 */
	private boolean isSunkHorizontal(int[] coordinates) {
		boolean result = true;
		int xCoordinate = coordinates[0];
		int yCoordinate = coordinates[1];
		for (int checkRight = yCoordinate + 1; checkRight < this.size; checkRight++) {
			CellType cellValue = this.getCell(xCoordinate, checkRight);
			if (cellValue == CellType.FOG || cellValue == CellType.MISS) {
				break;
			}
			if (cellValue == CellType.SHIP) {
				result = false;
				break;
			}
		}
		if (result) {
			for (int checkLeft = yCoordinate - 1; checkLeft >= 0; checkLeft--) {
				CellType cellValue = this.getCell(xCoordinate, checkLeft);
				
				if (cellValue == CellType.FOG || cellValue == CellType.MISS) {
					break;
				}
				if (cellValue == CellType.SHIP) {
					result = false;
					break;
				}
			}
			
		}
		return result;
	}
	/**
	 * a helper method to check if the ship has sunken vertically based on the last hit coordinate
	 * @param coordinates the last hit coordinates [X_coordinate, Y_coordinate].
	 * @return boolean indicator true if the ship has sunken or false otherwise
	 */
	private boolean isSunkVertical(int[] coordinates) {
		boolean result = true;
		int xCoordinate = coordinates[0];
		int yCoordinate = coordinates[1];
		for (int checkDown = xCoordinate + 1; checkDown < this.size; checkDown++) {
			CellType cellValue = this.getCell(checkDown, yCoordinate);
			if (cellValue == CellType.FOG || cellValue == CellType.MISS) {
				break;
			}
			if (cellValue == CellType.SHIP) {
				result = false;
				break;
			}
		}
		if (result) {
			for (int checkUp = xCoordinate - 1; checkUp >= 0; checkUp--) {
				CellType cellValue = this.getCell(checkUp, yCoordinate);
				
				if (cellValue == CellType.FOG || cellValue == CellType.MISS) {
					break;
				}
				if (cellValue == CellType.SHIP) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
	/**
	 * a helper method to check if the ship has sunken  based on the last hit coordinate
	 * @param coordinates the last hit coordinates [X_coordinate, Y_coordinate].
	 * @return boolean indicator true if the ship has sunken or false otherwise
	 */
	private boolean isSunk(int[] coordinates) {
		return this.isSunkVertical(coordinates) && this.isSunkHorizontal(coordinates);
	}
	
}
