package org.example.homework17_Server.model;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Client {

    public String name;
    public String dateTime;

    public String id;

    public Client(int counter) {
        this.name = "Client " + counter;
        this.dateTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
        this.id = UUID.randomUUID().toString();
    }
}
