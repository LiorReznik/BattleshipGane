package Battleship;
import java.util.Scanner;

/**
 * A class that manages the battleship game for two players
 */
public class Game {
	private final Scanner scan;;
	private final Board firstPlayerBoard;
	private final Board secondPlayerBoard;
	private  Board currentPlayerBoard;
	private  Board opponentsPlayerBoard;
	private PlayerType playerID;
	
	public Game() {
		this.firstPlayerBoard = new Board();
		this.secondPlayerBoard = new Board();
		this.scan = new Scanner(System.in);
		this.currentPlayerBoard = this.firstPlayerBoard;
		this.opponentsPlayerBoard = this.secondPlayerBoard;
		this.playerID = PlayerType.FIRST;
	}
	
	private void fillBoard() {
		for (int i = 0; i < 5; i++) {
			this.placeShip(ShipType.values()[i]);
		}
	}
	
	private void placeShip(ShipType ship) {
		int desiredSize = ship.getSize();
		System.out.printf("Enter the coordinates of the %s (%d cells):%n",ship.getName(), desiredSize);
		boolean flag = true;
		while(flag) {
			String start = PointUtility.convertCoordinates(this.scan.next());
			String end =  PointUtility.convertCoordinates(this.scan.next());
			if (PointUtility.checkLen(desiredSize, start, end)) {
				int[] coordinates = PointUtility.convertCoordinatesToInts(start, end);
				if (this.currentPlayerBoard.placeOnBoard(coordinates)) {
					System.out.println(this.currentPlayerBoard);
					flag = false;
				}
			} else {
				System.out.println("Error! Wrong length of the Submarine! Try again:\n");
			}
			
		}
	}
	
	private void switchPlayers() {
		System.out.println("Press Enter and pass the move to another player");
		this.scan.nextLine();
		while (!this.scan.nextLine().isEmpty()) {};
		if (this.playerID == PlayerType.FIRST) {
			this.playerID =  PlayerType.SECOND;
			this.currentPlayerBoard = this.secondPlayerBoard;
			this.opponentsPlayerBoard = this.firstPlayerBoard;
		} else {
			this.playerID =  PlayerType.FIRST;
			this.currentPlayerBoard = this.firstPlayerBoard;
			this.opponentsPlayerBoard = this.secondPlayerBoard;
		}
		this.currentPlayerBoard.revealBoard();
		this.opponentsPlayerBoard.hideBoard();
	}
	
	private void placeShipsForPlayer () {
		System.out.printf("Player %d, place your ships on the game field %n%n",this.playerID.getId());
		System.out.println(this.currentPlayerBoard);
		this.fillBoard();
	}
	private void printBoards() {
		System.out.print(this.opponentsPlayerBoard);
		System.out.println("---------------------");
		System.out.println(this.currentPlayerBoard);
	}
	
	public void play() {
		this.placeShipsForPlayer();
		this.switchPlayers();
		this.placeShipsForPlayer();
		boolean flag = false;
		while(!flag) {
			this.switchPlayers();
			flag = this.turn();
		}
		
		
	}
	private boolean turn() {
		this.printBoards();
		System.out.printf("Player %d, it's your turn:%n%n",this.playerID.getId());
		String msg = this.opponentsPlayerBoard.makeAShoot(PointUtility.convertCoordinates(this.scan.next()));
		System.out.println(msg);
		return "You sank the last ship. You won. Congratulations!".equals(msg);
	}

}
