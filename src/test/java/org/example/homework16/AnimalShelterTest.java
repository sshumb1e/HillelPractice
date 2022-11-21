package org.example.homework16;

import org.example.homework16.services.AnimalSerializer;
import org.example.homework16.services.AnimalShelter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.example.homework16.model.Animal;
import java.io.File;
import java.util.List;


public class AnimalShelterTest {

    private final AnimalShelter animalShelter = new AnimalShelter();
    private final AnimalSerializer animalSerializer = new AnimalSerializer();
    private final Animal dog = new Animal("sharik", "dog", 3, "pedal");

    private final Animal cat = new Animal("Murzik", "cat", 10, "Bengal");
    private final String tmpdir = System.getProperty("java.io.tmpdir");

    private final String path = new File(tmpdir + "animals.json").getAbsolutePath();

    @Before
    public void createDB() {
        animalSerializer.serialize(List.of(cat));
    }

    @After
    public void deletePath() {
        File file = new File(path);
        file.delete();
    }

    @Test
    public void add() {
        animalShelter.add(dog);
        Assert.assertTrue(animalSerializer.deserialize().contains(dog));
    }

    @Test
    public void delete() {
        animalShelter.delete(dog.getNickName());
        Assert.assertFalse(animalSerializer.deserialize().contains(dog));
    }

    @Test
    public void getByName() {;
        animalShelter.getByName(dog.getNickName());

    }

    @Test
    public void getAll() {
        animalShelter.getAll();
    }

    @Test
    public void searchByName() {
        List<Animal> animals = animalSerializer.deserialize();
        Assert.assertEquals(animals, List.of(cat));
    }
}
