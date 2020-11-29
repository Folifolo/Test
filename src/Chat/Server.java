package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

final public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private int clientNumbers;
    private ArrayList<Message> history;
    private final Object commonMessagesLock = new Object();

    public Server(int port) {
        clients = new ArrayList<>();
        history = new ArrayList<>();
        clientNumbers = 0;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldn't open a socket on port " + port);
        }
    }

    public final void openConnection() {
        while (true) {
            try {
                Client client = new Client(serverSocket.accept(), ++clientNumbers);
                clients.add(client);
                ServerThread serverThread = new ServerThread(client);
                serverThread.start();
            } catch (IOException e) {
                System.out.println("Error when waiting for a connection");
            }
        }

    }

    static final class Client {
        private Socket socket;
        private String name;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;

        Client(Socket socket, int number) {
            this.socket = socket;
            name = "Anonymous";
            try {
                reader = new ObjectInputStream(this.socket.getInputStream());
                writer = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendMessage(Message message) throws IOException {
            writer.writeObject(message);
            writer.flush();
        }

        void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        Message readMessage() throws IOException, ClassNotFoundException {
            return (Message) reader.readObject();
        }
    }

    class ServerThread extends Thread {
        Client client;
        Message receivedMsg;

        ServerThread(Client clientSocket) {
            this.client = clientSocket;
        }

        private void WelcomeMessages(Client client) throws IOException {
            for (Message msg : history) {
                client.sendMessage(msg);
            }
            client.sendMessage(new Message("Enter your name", "Server"));
        }

        @Override
        public void run() {
            Message clientMessage;
            try {
                WelcomeMessages(client);
                client.setName(client.readMessage().getText());
                client.sendMessage(new Message("Hello, " + client.getName(), "Server"));
                while (true) {
                    if ((clientMessage = client.readMessage()) != null) {
                        synchronized (commonMessagesLock) {
                            receivedMsg = new Message(clientMessage.getText(), client.getName());
                            history.add(receivedMsg);
                            if (history.size() > 10)
                                history.remove(0);
                            for (Client anotherClient : clients) {
                                anotherClient.sendMessage(receivedMsg);
                            }
                        }
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
