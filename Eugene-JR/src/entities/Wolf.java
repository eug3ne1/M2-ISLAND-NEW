package entities;

import map.GameMap;
import map.Location;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Wolf extends Animal implements Predator{

    private static final double INITIAL_HEALTH = 100.0;
    private static final double EAT_PROBABILITY = 0.5;  // 50% ймовірність поїсти
    private static final double MOVE_PROBABILITY = 0.3; // 30% ймовірність пересунутися
    private static final double REPRODUCE_PROBABILITY = 0.2;// 20% ймовірність розмножитися
    private double health;
    private Random random;

    public Wolf(GameMap gameMap, Location currentLocation) {
        super(INITIAL_HEALTH, gameMap, currentLocation);
        this.random = new Random();
        this.health = INITIAL_HEALTH;
    }

    @Override
    public void performAction() {

        int action = random.nextInt(3); // 0 - eat, 1 - move, 2 - reproduce
        switch (action) {
            case 0 -> eat();
            case 1 -> move();
            case 2 -> reproduce();
        }


    }

    @Override
    public void eat() {
        synchronized (currentLocation) {

            List<Animal> animalsInCell = currentLocation.getAnimals();

            // Фільтруємо потенційних жертв (не хижаки)
            List<Animal> preyList = animalsInCell.stream()
                    .filter(animal -> animal instanceof Herbivore)
                    .toList();

            if (!preyList.isEmpty()) {
                // Випадковий вибір жертви
                Animal prey = preyList.get(random.nextInt(preyList.size()));

                // Шанс з'їсти жертву
                int eatChance = 60; // Ймовірність 60%, що вовк з'їсть жертву
                if (random.nextInt(100) < eatChance) {
                    // Видаляємо жертву з клітинки
                    currentLocation.removeAnimal(prey);
                    increaseHealth(20); // Наприклад, додаємо 20% до здоров’я
                    System.out.println("Wolf ate " + prey.getClass().getSimpleName());
                } else {
                    System.out.println("Wolf failed to catch " + prey.getClass().getSimpleName());
                }
            } else {
                System.out.println("Wolf found no prey to eat.");
            }
        }

    }

    private void increaseHealth(int i) {
        this.health +=i;
    }

    @Override
    public void move() {


    }



    @Override
    public void reproduce() {
        if (health > 70) {
            decreaseHealth(20);
            System.out.println("Wolf reproduces!");
        }

    }
}
