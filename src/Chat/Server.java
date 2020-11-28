package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static Chat.Chat.QUIT_COMMANDS;

final public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private int clientNumbers;
    private ArrayList<Message> history;
    Object commonMessagesLock = new Object();

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
        while(true) {
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
        private BufferedReader reader;
        private PrintWriter writer;

        Client(Socket socket, int number) {
            this.socket = socket;
            name = "Anonymous";
            try {
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                writer = new PrintWriter(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendMessage(Message message) {
            writer.println(message);
            writer.flush();
        }

        void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        boolean isReaderReady() throws IOException {
            return reader.ready();
        }

        String readLine() throws IOException {
            return reader.readLine();
        }
    }

    class ServerThread extends Thread {
        Client client;
        Message receivedMsg;

        ServerThread (Client clientSocket) {
            this.client = clientSocket;
        }

        private void WelcomeMessages(Client client) {
            for(Message msg : history) {
                client.sendMessage(msg);
            }
            client.sendMessage(new Message("Enter your name", "Server"));
        }

        @Override
        public void run() {
            try {
                WelcomeMessages(client);
                client.setName(client.readLine());
                client.sendMessage(new Message("Hello, " + client.getName(), "Server"));
                while(true) {
                    if(client.isReaderReady()) {
                        synchronized (commonMessagesLock) {
                            receivedMsg = new Message(client.readLine(), client.getName());
                            history.add(receivedMsg);
                            if (history.size() > 10)
                                history.remove(0);
                            for (Client anotherClient : clients) {
                                anotherClient.sendMessage(receivedMsg);
                            }
                        }
                    }
                    if(Arrays.asList(QUIT_COMMANDS).contains(receivedMsg)) {
                        for (Client anotherClient : clients) {
                            anotherClient.sendMessage(new Message("User "+ client.getName() + " is disconnected"));
                        }
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
