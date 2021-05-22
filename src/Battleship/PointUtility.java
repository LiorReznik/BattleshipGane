package Battleship;

/**
 * A helper class to make manipulations on points
 * (convert them to strings or ints, checks of distance, direction)
 */
final class PointUtility {
	private PointUtility() {}
	
	/**
	 * A helper function that gets the desired size of the ship and two strings that represents
	 * two points
	 * the function checks if the desired size is equal to the len of the ship (represented by the points)
	 * @param size desired size of the ship
	 * @param start start point where charAt(0) is X-coordinate and charAt(1) is Y-coordinate
	 * @param end end point where charAt(0) is X-coordinate and charAt(1) is Y-coordinate
	 * @return boolean indicator
	 */
	static boolean checkLen(int size, String start, String end) {
		boolean result;
		try {
			int distanceX = (start == null) ? 0 : (Math.abs(start.charAt(0) - end.charAt(0)) + 1);
			int distanceY = (end == null) ? 0 : (Math.abs(start.charAt(1) - end.charAt(1)) + 1);
			result = distanceX == size || distanceY == size;
		} catch (NullPointerException | StringIndexOutOfBoundsException e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * A helper function that gets a Point as String (value of the Coordinates Enum)
	 * and converts it to String that represents the coordinates of the board
	 * @param coords coordinates of one point represented as string where charAt(0) is
	 *                  x_coordinate  and charAt(1) is Y_coordinate ex: 11
	 * @return 2-digits string where the first char is the coordinate of X and the second of Y.
	 */
	static String convertCoordinates(String coords) {
		return Coordinates.getCoordinates(coords.toUpperCase());
	}
	/**
	 * function that gets two points as int coordinates within an array [X_start, X_end, Y_start, Y_end]
	 * and checks the direction of the ship
	 * Vertical, horizontal or none.
	 * @param coordinates
	 * @return direction of the ship vertical, none or horizontal.
	 */
	static  Direction isVertical(int[] coordinates) {
		boolean xPosition = coordinates[0] == coordinates[1] - 1;
		boolean yPosition = coordinates[2] == coordinates[3] - 1;
		if (xPosition && !yPosition) {
			return Direction.HORIZONTAL;
		} else if (yPosition && !xPosition) {
			return Direction.VERTICAL;
		}
		return Direction.NONE;
		
	}
	
	/**
	 * A helper function that checks if the points are swapped (
	 * @param coordinates [X_start, X_end, Y_start, Y_end]
	 * @return boolean indicator
	 */
	static  void swap(int[] coordinates) {
		if (coordinates[0] >= coordinates[1]) {
			swap(coordinates,0,1);
		} else if (coordinates[2] >= coordinates[3]) {
			swap(coordinates,2,3);
		}
	}
	
	private static void swap(int[] coordinates, int ind1,int ind2){
		coordinates[ind1] += coordinates[ind2];
		coordinates[ind2] = coordinates[ind1] - coordinates[ind2];
		coordinates[ind1] -= coordinates[ind2];
		coordinates[ind1] -= 1;
		coordinates[ind2] += 1;
	}

	/**
	 * A function that gets two points (start and end)  as strings and
	 * returns them as array of int coordinates.
	 * @param start
	 * @param end
	 * @return array of ints [X-start, X-end, Y-start, Y-end]
	 */
	static int[] convertCoordinatesToInts(String start, String end) {
		if (start == null || end == null) {
			return null;
		}
		return new int[]{start.charAt(0) - '0', end.charAt(0) - '0' + 1,
				start.charAt(1) - '0', end.charAt(1) - '0' + 1};
	}
	
	/**
	 * A function that gets point as string and returns it as array of int coordinates
	 * @param coordinates
	 * @return array of ints [X-coordinate, Y-coordinate]
	 */
	static int[] convertCoordinatesToInts(String coordinates) {
		return new int[]{coordinates.charAt(0) - '0', coordinates.charAt(1) - '0'};
	}
	
}
