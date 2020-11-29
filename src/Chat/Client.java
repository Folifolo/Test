package Chat;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;


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
            return;
        }

        try {
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            ReaderThread serverReader = null;

            Message keyboardMessage = null;
            Message serverMessage = null;
            if ((serverMessage = (Message) reader.readObject()) != null)
                System.out.println(serverMessage);

            while (true) {
                if (keyboardReader.ready()) {
                    keyboardMessage = new Message(keyboardReader.readLine());
                    writer.writeObject(keyboardMessage);
                    writer.flush();
                }
                if (serverReader == null) {
                    serverReader = new ReaderThread(reader);
                    serverReader.start();
                } else if (serverReader.message != null) {
                    System.out.println(serverReader.message);
                    serverReader = null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection error");
            return;
        }

    }


    public static void main(String[] args) {
        Thread client = new Thread(() -> new Client().connect("localhost", 1234));
        client.start();
    }
}
