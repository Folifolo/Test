package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static Chat.Chat.QUIT_COMMANDS;

final public class Server {
    private final String HELLO_MSG = "Hello, client # %d";
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private int clientNumbers;

    public Server(int port) {
        clients = new ArrayList<>();
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
        private int number;
        private BufferedReader reader;
        private PrintWriter writer;

        Client(Socket socket, int number) {
            this.socket = socket;
            this.number = number;
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

        int getNumber() {
            return number;
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

        @Override
        public void run() {
            try {
                client.sendMessage(new Message(String.format(HELLO_MSG, client.getNumber()), "Server"));
                while(true) {
                    {
                        if(client.isReaderReady()) {
                            receivedMsg = new Message(client.readLine(), String.valueOf(client.number));
                            for(Client anotherClient : clients)
                                anotherClient.sendMessage(receivedMsg);
                        }
                        if(Arrays.asList(QUIT_COMMANDS).contains(receivedMsg)) {
                            System.out.println(String.format("Server # %d is shutting down", client.getNumber()));
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
