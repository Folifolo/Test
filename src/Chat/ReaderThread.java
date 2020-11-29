package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReaderThread extends Thread {
    ObjectInputStream reader;
    Message message;
    boolean isMessageRead;
    int i, j;

    ReaderThread(ObjectInputStream reader){
        this.reader = reader;
        isMessageRead = true;
    }

    @Override
    public void run() {
        while(true) {
            if(message == null) {
                try {
                    message = (Message) reader.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Message readMessage() throws InterruptedException {
        Thread.sleep(500);
        if(message!=null) {
            Message tmp = message;
            isMessageRead = true;
            message = null;
            return tmp;
        }
        return null;
    }


}
