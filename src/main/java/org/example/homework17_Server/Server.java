package org.example.homework17_Server;

import lombok.RequiredArgsConstructor;
import org.example.homework17_Server.model.Client;
import org.example.homework17_Server.service.ServerSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<ServerMenu> serverSessions;
    private final ServerSerializer serializer = ServerSerializer.getSerializer();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        serverSessions = new ArrayList<>();
    }

    public void getConnection() throws IOException {


        while (true) {

            try {
                socket = serverSocket.accept();
                System.out.println("New client has connected to server " + socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Opened new session for connection");
                Thread thread = new ServerMenu(dataInputStream,dataOutputStream,socket,this);
                thread.start();

            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public void addSession(ServerMenu session) {

        if (!serializer.deserialize().isEmpty()) {
            List<Client> clients = new ArrayList<Client>(serializer.deserialize());
            clients.add(session.getClient());
            serializer.serialize(clients);
        } else { serializer.serialize(new ArrayList<>(List.of(session.getClient())));}
        serverSessions.add(session);
    }

    public void removeSession(ServerMenu session) {

        serverSessions.remove(session);

        serializer.serialize(serializer.deserialize().stream()
                .filter(s -> s.getName().equals(session.getClient().getName()))
                .collect(Collectors.toList()));
    }




}
