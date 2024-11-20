package map;

public interface GameMap {

    void createMap();

    boolean isValidCell(int row, int coll);

    Location getLocation(int row, int coll);


}
