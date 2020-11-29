package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReaderThread extends Thread {
    private ObjectInputStream reader;
    Message message;

    ReaderThread(ObjectInputStream reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        while (true) {
            if (message == null) {
                try {
                    message = (Message) reader.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Server is unavailable");
                    return;
                }
            }
        }
    }
}
