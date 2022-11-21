package org.example.homework16;

import org.example.homework16.services.AnimalSerializer;
import org.junit.*;
import org.example.homework16.model.Animal;
import java.io.File;
import java.util.List;



public class AnimalSerializerTest {

    private final AnimalSerializer animalSerializer = new AnimalSerializer();
    private final Animal cat = new Animal("Murzik", "cat", 10, "Bengal");
    private final Animal dog = new Animal("barsik", "dog", 2, "Akita");
    private final String tmpdir = System.getProperty("java.io.tmpdir");
    private final String path = new File(tmpdir + "animals.json").getAbsolutePath();



    @Before
    public void createDB() {
        animalSerializer.serialize(List.of(dog, cat));
    }

    @Test
    public void deserialize() {
        List<Animal> animals = animalSerializer.deserialize();
        Assert.assertEquals(animals, List.of(dog, cat));
    }

    @After
    public void deletePath() {
        File file = new File(path);
        file.delete();
    }
}
