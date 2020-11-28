package Chat;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

import static Chat.Chat.QUIT_COMMANDS;

public class Client {
    Socket clientSocket;

    Client() {
        clientSocket = new Socket();
    }

    void connect(String address, int port) {
        try {
            clientSocket.connect(new InetSocketAddress(address, port));

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            String reply;
            System.out.println(reader.readLine());

            while (true)
            {
                writer.println(keyboardReader.readLine());
                writer.flush();
                reply = reader.readLine();
                System.out.println(reply);
                if(Arrays.asList(QUIT_COMMANDS).contains(reply)) {
                    System.out.println("Client if shutting down");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
