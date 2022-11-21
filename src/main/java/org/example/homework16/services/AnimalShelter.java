package org.example.homework16.services;

import org.example.homework16.model.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalShelter {

    private final AnimalSerializer animalSerializer = new AnimalSerializer();

    public void add(Animal animal) {
        if (animal != null) {
            if (!animalSerializer.deserialize().isEmpty()) {
                List<Animal> animals = new ArrayList<>(animalSerializer.deserialize());
                animals.add(animal);
                animalSerializer.serialize(animals);
                System.out.println("\n" + "Congratulations, your pet " + animal.getNickName() + " has been added to the list!!!");
            } else {
                animalSerializer.serialize(new ArrayList<>(List.of(animal)));
            }
        }
        if (animal == null) {
            System.out.println("\u001B[31m" + "Please repeat operation !!!");
        }
    }

    public void getAll() {
        if (!animalSerializer.deserialize().isEmpty()) {
            System.out.println(animalSerializer.deserialize());
        } else {
            System.out.println("\u001B[31m" + "File DB not found !!!");
        }
    }

    public void getByName(String nickName) {
        if (searchByName(nickName).isPresent()) {
            System.out.println(searchByName(nickName));
        }
        System.out.println("\u001B[31m" + "Please repeat operation !!!");
    }

    public void delete(String nickName) {
        if (searchByName(nickName).isPresent()) {
            List<Animal> animals = animalSerializer.deserialize()
                    .stream().filter(animal -> !animal.getNickName().equals(nickName)).toList();
            animalSerializer.serialize(animals);
            System.out.println("\n" + "Congratulations, your pet " + nickName + " has been removed from the list!!!");
        } else System.out.println("\u001B[31m" + "Please repeat operation !!!");
    }

    public Optional<Animal> searchByName(String nickName) {
        if (!animalSerializer.deserialize().isEmpty()) {
            Optional<Animal> animal = animalSerializer.deserialize()
                    .stream()
                    .filter(a -> a.getNickName().equals(nickName)).findFirst();
            if (animal.isPresent()) {
                return animal;
            }
            System.out.println("\u001B[31m" + "Pet with nickName" + nickName + " not found !!!");
        }
        System.out.println("\u001B[31m" + "File DB not found !!!");
        return Optional.empty();
    }
}
