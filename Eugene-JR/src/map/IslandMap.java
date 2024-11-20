package map;

import entities.Predator;

import java.util.concurrent.ExecutorService;

public class IslandMap implements GameMap{
    private final Location[][] grid;
    private final ExecutorService executor;

    public IslandMap(Location[][] grid, ExecutorService executor) {
        this.grid = grid;
        this.executor = executor;
    }

    @Override
    public void createMap() {

    }

    @Override
    public boolean isValidCell(int row, int coll) {
        return false;
    }


    public boolean isValidLocation(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public Location getLocation(int row, int col) {
        return grid[row][col];
    }
}

