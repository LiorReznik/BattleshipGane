package Battleship;

enum ShipType {
    /**
     *
     */
    AIRCRAFT(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");
    
    private final int size;
    private final String name;
    
    ShipType(int size, String name) {
        this.size = size;
        this.name = name;
    }

    int getSize() {
        return this.size;
    }
    String getName() {
        return this.name;
    }
}
