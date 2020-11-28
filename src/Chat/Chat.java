package Chat;

import java.io.IOException;

public class Chat {
    static String[] QUIT_COMMANDS = {"quit", "q"};

    public static void main(String[] args) throws IOException {
        Thread client1 = new Thread(() -> new Client().connect("localhost", 1234));
        client1.start();
        Server server  = new Server(1234);
        server.openConnection();
    }
}
