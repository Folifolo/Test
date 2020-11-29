package Chat;

import java.io.IOException;

public class Chat {

    public static void main(String[] args) throws IOException {
        Thread client1 = new Thread(() -> new Client().connect("localhost", 1234));
        Server server = new Server(1234);
        client1.start();
        server.openConnection();

    }
}
