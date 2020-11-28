package Chat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    String text;
    Calendar date;
    String sender;

    Message(String text, Calendar date, String sender) {
        this.text = text;
        this.date = date;
        this.sender = sender;
    }
    Message(String text) {
        this(text,  new GregorianCalendar(),  "Anonymous");
    }
    Message(String text, String sender) {
        this(text,  new GregorianCalendar(),  sender);
    }

    static String calendarToString(Calendar date) {
        return date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
    }

    @Override
    public String toString() {
        return String.format("%s %s says: \"%s\"", calendarToString(date), sender, text);
    }
}
