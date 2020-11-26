import java.util.Stack;

public class UndoStringBuilder{

    private OnSBChangeListener listener;

    public void listenerSetter(OnSBChangeListener listener) {
        this.listener=listener;
    }

    private void notifyListener(){
        if(listener!=null)
            listener.change(this);
    }

    interface Undoable {
        void undo();
    }

    public void undoOperation() {
        buffer.pop().undo();
    }

    private StringBuilder stringBuilder;
    private Stack<Undoable> buffer = new Stack<Undoable>();

    public UndoStringBuilder(String str) {
        stringBuilder = new StringBuilder(str);
    }

    public UndoStringBuilder append(boolean b) {
        stringBuilder.append(b);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length()- Boolean.toString(b).length(),
                stringBuilder.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder append(char c) {
        stringBuilder.append(c);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length()- 1,
                stringBuilder.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder append(char[] str) {
        stringBuilder.append(str);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - str.length,
                stringBuilder.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder append(String str) {
        stringBuilder.append(str);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - str.length(),
                stringBuilder.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder append(double d) {
        stringBuilder.append(d);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - Double.toString(d).length(),
                stringBuilder.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder delete(int start, int end) {
        String deletedSeq = stringBuilder.substring(start, end);
        stringBuilder.delete(start, end);
        buffer.push(() -> stringBuilder.insert(start, deletedSeq));
        notifyListener();
        return this;
    }

    public UndoStringBuilder insert(int offset, String str){
        stringBuilder.insert(offset, str);
        buffer.push(() -> stringBuilder.delete(offset, offset+str.length()));
        notifyListener();
        return this;
    }

    public UndoStringBuilder appendCodePoint(int codePoint) {
        stringBuilder.appendCodePoint(codePoint);
        buffer.push(() -> stringBuilder.deleteCharAt(stringBuilder.length()-1));
        notifyListener();
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

interface OnSBChangeListener {
    void change(UndoStringBuilder stringBuilder);
}

class MyListener implements OnSBChangeListener {
    @Override
    public void change(UndoStringBuilder stringBuilder) {
        System.out.println("String changed: " + stringBuilder);
    }
}
