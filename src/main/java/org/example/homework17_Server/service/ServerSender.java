package org.example.homework17_Server.service;

import org.example.homework17_Server.Server;
import org.example.homework17_Server.ServerMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerSender {


    private List<ServerMenu> serverSessions = new ArrayList<>();
    private DataOutputStream dataOutputStream;

    public void broadcast(ServerMenu sesOne, String msg) throws IOException {

        for (ServerMenu session : serverSessions) {
            if (session!=sesOne) {
               dataOutputStream.writeUTF(msg);
            }
        }
    }

    public void receiveFile(String fileName, DataInputStream inputStream) throws IOException {

        int bytes = 0;

        Path path = Paths.get(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(path.getFileName().toString());
        long size = inputStream.readLong();
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = inputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;
        }
        fileOutputStream.close();
    }
}
