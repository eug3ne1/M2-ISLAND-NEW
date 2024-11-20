package entities;

import map.GameMap;
import map.Location;

import java.util.Random;

public abstract class Animal implements Organism{
    protected double health;
    protected boolean isAlive;
    protected GameMap gameMap;
    protected Random random = new Random();
    protected Location currentLocation;

    public Animal(double initialHealth, GameMap gameMap, Location currentLocation) {
        this.health = initialHealth;
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getHealth() {
        return health;
    }

    public abstract void performAction();  // Виконати одну дію (пересування, поїдання, розмноження)

    public void decreaseHealth(double amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.isAlive = false;
        }
    }

    public abstract void eat();
    public void move(){
        // Отримуємо координати поточної локації
        int currentRow = currentLocation.getRow();
        int currentCol = currentLocation.getCol();

        // Генеруємо випадковий напрямок (вгору, вниз, вліво, вправо)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[] direction = directions[random.nextInt(directions.length)];

        int newRow = currentRow + direction[0];
        int newCol = currentCol + direction[1];

        // Перевіряємо, чи нова локація в межах карти
        if (gameMap.isValidCell(newRow, newCol)) {
            Location newLocation = gameMap.getLocation(newRow, newCol);
            synchronized (newLocation) {
                // Перевіряємо, чи є місце для нової тварини
                if (newLocation.hasSpace()) {
                    // Видаляємо себе з поточної клітинки
                    synchronized (currentLocation) {
                        currentLocation.removeAnimal(this);
                    }

                    // Додаємо себе до нової клітинки
                    newLocation.addAnimal(this);
                    this.currentLocation = currentLocation;

                    System.out.println(this.getClass().getSimpleName() + " moved to new Currentlocation (" + newRow + ", " + newCol + ")");
                    return;
                };
            }
        }
    }


}
