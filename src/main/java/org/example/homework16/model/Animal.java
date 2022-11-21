package org.example.homework16.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Animal {

    @NonNull
    private final String dateOfArrive = LocalDate.now().toString();
    private String nickName;
    private String animalType;
    private int age;
    private String breed;
}
