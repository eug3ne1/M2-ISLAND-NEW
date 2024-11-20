package map;

import entities.Animal;
import entities.Herbivore;
import entities.Plant;
import entities.Predator;

import java.util.ArrayList;
import java.util.List;

public class Location implements Runnable{
    private final int row;
    private final int col;
    private final List<Animal> animals;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
        this.animals = new ArrayList<>();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public synchronized int countAllAnimals() {
        return animals.size();
    }

    public synchronized int countPredators() {
        return (int) animals.stream().filter(a -> a instanceof Predator).count();
    }

    public synchronized int countHerbivores() {
        return (int) animals.stream().filter(a -> a instanceof Herbivore).count();
    }

    @Override
    public void run() {
        synchronized (this) {
            animals.forEach(Animal::performAction);
            // Видалення мертвих тварин
            animals.removeIf(animal -> !animal.isAlive());
        }
    }

    public boolean hasSpace(){
        return true;
    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

    }



