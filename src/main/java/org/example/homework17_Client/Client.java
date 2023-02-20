package org.example.homework17_Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private InetAddress addr;
    private int port;

    public Client(int port) {
        try {
            addr = InetAddress.getByName("localhost");
            this.port = port;
        } catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    public Client() {
    }

    public void Connection() {
        try {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket(addr, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());


            while(true) {

                System.out.println(dataInputStream.readUTF());
                String request = scanner.nextLine();
                dataOutputStream.writeUTF(request);

                if (request.equals("Exit")) {
                    System.out.println("Connection closing... : " + socket);
                    socket.close();
                    break;
                }

                if (request.equals("File")) {
                    System.out.println("Please enter file name");
                    String fileName = scanner.nextLine();
                    dataOutputStream.writeUTF(fileName);
                    sendFile(fileName);
                    System.out.println("File send and exit");
                    break;

                }
                String response = dataInputStream.readUTF();
                System.out.println(response);
            }

            dataInputStream.close();
            dataOutputStream.close();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void sendFile(String fileName) throws Exception {
        int bytes = 0;
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while((bytes = fileInputStream.read(buffer)) != - 1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}
