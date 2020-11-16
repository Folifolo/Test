import java.util.Stack;

public class UndoStringBuilder{

    interface Undoable {
        void undo();
    }

    void undoOperation() {
        buffer.pop().undo();
    }

    StringBuilder stringBuilder;
    Stack<Undoable> buffer = new Stack<Undoable>();

    UndoStringBuilder(String str) {
        stringBuilder = new StringBuilder(str);
    }

    UndoStringBuilder append(boolean b) {
        stringBuilder.append(b);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length()- Boolean.toString(b).length(),
                stringBuilder.length()));
        return this;
    }

    UndoStringBuilder append(char c) {
        stringBuilder.append(c);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length()- 1,
                stringBuilder.length()));
        return this;
    }

    UndoStringBuilder append(char[] str) {
        stringBuilder.append(str);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - str.length,
                stringBuilder.length()));
        return this;
    }

    UndoStringBuilder append(String str) {
        stringBuilder.append(str);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - str.length(),
                stringBuilder.length()));
        return this;
    }

    UndoStringBuilder append(double d) {
        stringBuilder.append(d);
        buffer.push(() -> stringBuilder.delete(
                stringBuilder.length() - Double.toString(d).length(),
                stringBuilder.length()));
        return this;
    }

    UndoStringBuilder delete(int start, int end) {
        String deletedSeq = stringBuilder.substring(start, end);
        stringBuilder.delete(start, end);
        buffer.push(() -> stringBuilder.insert(start, deletedSeq));
        return this;
    }

    UndoStringBuilder insert(int offset, String str){
        stringBuilder.insert(offset, str);
        buffer.push(() -> stringBuilder.delete(offset, offset+str.length()));
        return this;
    }

    UndoStringBuilder appendCodePoint(int codePoint) {
        stringBuilder.appendCodePoint(codePoint);
        buffer.push(() -> stringBuilder.deleteCharAt(stringBuilder.length()-1));
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
