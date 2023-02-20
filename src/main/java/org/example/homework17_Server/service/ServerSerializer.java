package org.example.homework17_Server.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.homework17_Server.model.Client;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ServerSerializer {

    private static ServerSerializer serializer;

    private ServerSerializer() {

    }
    public static ServerSerializer getSerializer() {
        if (serializer == null) serializer = new ServerSerializer();
        return serializer;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String path = "src/main/resources/serverClients.json";

    public void serialize(List<Client> clients) {
        try {
            objectMapper.writeValue(new File(path),clients);
        } catch (Exception e) {
            System.out.println("File couldn't be created");
        }
    }

    public List<Client> deserialize() {

        try {
            return List.of(objectMapper.readValue(path, Client[].class));
        } catch (IOException e) {

        }
        return Collections.emptyList();

    }

}
