package org.example.homework17_Server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.homework17_Server.model.Client;
import org.example.homework17_Server.service.ServerSender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ServerMenu extends Thread {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;

    final Socket socket;
    private Server server;
    private Client client;
    private static int counter = 1;
    private ServerSender serverSender;


    public ServerMenu(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, Server server) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        client = new Client(counter++);
        this.socket = socket;
        this.server = server;
        server.addSession(this);
    }
 @Override
    public void run()
    {
        String response;

        while (true)
        {
            try {

                dataOutputStream.writeUTF("Please make your choose [Date | Time | File | Exit]");

                response = dataInputStream.readUTF();

                serverSender.broadcast(this, "New client -"+ this.getClient().getName() + " connected!!!" );

                if(response.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    serverSender.broadcast(this, this.getName() + " closed connection");
                    this.socket.close();
                    System.out.println("Connection closed");
                    server.removeSession(this);
                    break;
                }

                Date date = new Date();

                switch (response) {
                    case "Date" -> {
                        dataOutputStream.writeUTF(dateFormat.format(date));
                    }
                    case "Time" -> {
                        dataOutputStream.writeUTF(timeFormat.format(date));
                    }
                    case "File" -> {
                        serverSender.receiveFile(dataInputStream.readUTF(), dataInputStream);
                        dataOutputStream.writeUTF("File send");
                    }
                    default -> {
                        dataOutputStream.writeUTF("Invalid input");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try
        {

            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
