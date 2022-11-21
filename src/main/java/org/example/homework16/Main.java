package org.example.homework16;

import org.example.homework16.model.Animal;
import org.example.homework16.services.AnimalShelter;
import java.util.Scanner;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {

        final AnimalShelter animalShelter = new AnimalShelter();
        final Scanner scanner = new Scanner(System.in);

        out.printf("%s %n", "Please choose operation");
        out.println("_____________________________");
        out.printf("%-27s %d %n", "ANIMAL REGISTRATION ENTER: ", 1);
        out.printf("%-27s %d %n", "GET ANIMAL BY NAME ENTER: ", 2);
        out.printf("%-27s %d %n", "GET ALL ANIMALS ENTER: ",  3);
        out.printf("%-27s %d %n", "ANIMAL DEPARTURE ENTER: ", 4);
        out.println("_____________________________");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                out.print("Please fill in the data about the animal! \n"
                        .concat("Pet`s nickname: "));
                String nickName = scanner.next().toLowerCase();
                out.print("Pet`s type: ");
                String animalType = scanner.next().toLowerCase();
                out.print("Pet`s age: ");
                int age = scanner.nextInt();
                out.print("Pet`s breed: ");
                String breed = scanner.next().toLowerCase();
                animalShelter.add(Animal.builder()
                        .nickName(nickName)
                        .animalType(animalType)
                        .age(age).breed(breed)
                        .build());
                scanner.close();
                break;
            case 2:
                out.println("Search pets by name!");
                out.print("Pet`s nickname: ");
                nickName = scanner.next().toLowerCase();
                animalShelter.getByName(nickName);
                scanner.close();
                break;
            case 3:
                animalShelter.getAll();
                break;
            case 4:
                out.println("Departure pets by name!");
                out.print("Pet`s nickname: ");
                nickName = scanner.next().toLowerCase();
                animalShelter.delete(nickName);
                scanner.close();
                break;
            default:
                System.out.println("\u001B[31m" + "WRONG NUMBER !!!");
        }
    }
}
