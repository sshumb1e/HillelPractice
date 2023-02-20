package org.example.homework17_Client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTest {

    private ServerSocket serverSocket;
    private DataOutputStream serverOut;

    @Before
    public  void Server() throws IOException {
        serverSocket = new ServerSocket(9028);
        new Thread(() -> {
            try {
                Socket socket = serverSocket.accept();
                serverOut = new DataOutputStream(socket.getOutputStream());
                serverOut.writeUTF("Connection accept");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Test
    public void connection() throws IOException {
        Socket client = new Socket("localhost",9028);
        DataInputStream clientIn = new DataInputStream(client.getInputStream());
        Assert.assertEquals("Connection accept", clientIn.readUTF());
        client.close();
    }

    @After
    public void Close() throws IOException {
        serverSocket.close();
    }
}
