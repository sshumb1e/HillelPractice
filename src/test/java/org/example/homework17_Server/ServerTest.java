package org.example.homework17_Server;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private DataOutputStream serverOut;
    private DataInputStream serverIn;
    private ServerSocket serverSocket;
    private Semaphore lock = new Semaphore(0);



    @Test
    public void getConnection() throws Exception {
        serverSocket = new ServerSocket(9028);
        listen(serverSocket);

        Socket client = new Socket("localhost",9028);
        DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
        DataInputStream clientIn = new DataInputStream(client.getInputStream());

        System.out.println("Lock");
        lock.acquire();
        System.out.println("Acquired lock");

        write(clientOut,"Server first word: Hello World!");
        assertRead(serverIn,"Server first word: Hello World!");

        write(serverOut,"Server solution!");
        assertRead(clientIn,"Server solution!");

        printWrite(clientOut,"How are u doing mate?");
        assertRead(serverIn,"How are u doing mate?");

        printWrite(serverOut,"Show me ur brain");
        assertRead(clientIn,"Show me ur brain");

        client.close();
        serverSocket.close();
    }

    private void write(OutputStream outputStream, String line) throws IOException {
        outputStream.write(line.getBytes());
        outputStream.flush();
    }

    private void printWrite(OutputStream out, String line) throws IOException {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(line);
        printWriter.flush();
    }

    private void assertRead(InputStream inputStream, String expected) throws IOException {
        assertEquals("Too few bytes available for reading: ", expected.length(), inputStream.available());

        byte[] buffer = new byte[expected.length()];
        inputStream.read(buffer);
        assertEquals(expected, new String(buffer));
    }


    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = new DataOutputStream(socket.getOutputStream());
                serverIn = new DataInputStream(socket.getInputStream());

                lock.release();
                System.out.println("Released lock");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
