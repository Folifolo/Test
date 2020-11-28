package Chat;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

import static Chat.Chat.QUIT_COMMANDS;

public class Client {
    private Socket clientSocket;

    Client() {
        clientSocket = new Socket();
    }

    void connect(String address, int port) {
        try {
            clientSocket.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            System.out.println("Can't connect server");
        }
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            String message = null;
            System.out.println(reader.readLine());

            while (true)
            {
                if(keyboardReader.ready()) {
                    message = keyboardReader.readLine();
                    writer.println(message);
                    writer.flush();
                }
                if(reader.ready())
                    System.out.println(reader.readLine());
                if(Arrays.asList(QUIT_COMMANDS).contains(message)) {
                    System.out.println("Client if shutting down");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error");
        }

    }


    public static void main(String[] args) {
        Thread client = new Thread(() -> new Client().connect("localhost", 1234));
        client.start();
    }
}
