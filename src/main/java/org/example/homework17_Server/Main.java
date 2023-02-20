package org.example.homework17_Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        new Server(new ServerSocket(9028)).getConnection();
    }
}
