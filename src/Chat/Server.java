package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

final public class Server {
    private static int MAX_USERS;
    private static int PORT;
    private static int MAX_MESSAGES;
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private ArrayList<Message> history;
    private final Object commonMessagesLock = new Object();
    private final Object clientWaitLock = new Object();

    public Server() {
        clients = new ArrayList<>();
        history = new ArrayList<>();

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/Chat/config.XML");
            property.load(fis);
            MAX_USERS = Integer.valueOf(property.getProperty("MAX_USERS"));
            PORT = Integer.valueOf(property.getProperty("PORT"));
            MAX_MESSAGES = Integer.valueOf(property.getProperty("MAX_MESSAGES"));
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Couldn't open a socket on port " + PORT);
        }
    }

    public final void openConnection() {
        while (true) {
            synchronized (clientWaitLock) {
                try {
                    if(clients.size() >= MAX_USERS)
                        clientWaitLock.wait();

                    Client client = new Client(serverSocket.accept());
                    clients.add(client);
                    ServerThread serverThread = new ServerThread(client);
                    serverThread.start();

                    clientWaitLock.notifyAll();

                } catch (IOException e) {
                    System.out.println("Error when waiting for a connection");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static final class Client {
        private Socket socket;
        private String name;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;

        Client(Socket socket) {
            this.socket = socket;
            name = "Anonymous";
            try {
                reader = new ObjectInputStream(this.socket.getInputStream());
                writer = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendMessage(Message message) {
            try {
                writer.writeObject(message);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                messageToAll(new Message("Client " + client.getName() + " has connected", "Server"));
                while (true) {
                    if ((clientMessage = client.readMessage()) != null) {
                        synchronized (commonMessagesLock) {
                            receivedMsg = new Message(clientMessage.getText(), client.getName());
                            messageToAll(receivedMsg);
                        }
                    }
                }

            } catch (IOException e) {
                synchronized (clientWaitLock) {
                    clients.remove(client);
                    messageToAll(new Message("Client " + client.getName() + " has disconnected" , "Server"));
                    clientWaitLock.notifyAll();
                }
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void messageToAll(Message m) {
            for (Client client : clients) {
                client.sendMessage(m);
            }
            history.add(m);
            if (history.size() > MAX_MESSAGES)
                history.remove(0);
        }
    }
}
