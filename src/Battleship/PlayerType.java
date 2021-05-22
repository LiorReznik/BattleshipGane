package Battleship;

enum PlayerType {
	FIRST((byte)1),
	SECOND((byte)2);
	private final byte id;
	PlayerType(byte id) {
		this.id = id;
	}
	byte getId() {
		return id;
	}
}
