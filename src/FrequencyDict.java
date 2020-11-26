import java.util.HashMap;

public class FrequencyDict {

    private static String normaliseText(String text) {

        return text.replaceAll("[^a-zA-Z ]", "").toLowerCase().replaceAll("\\s{2,}", " ").trim();
    }

    static HashMap<Character, Integer> calculateCharacters(String text) {
        HashMap<Character, Integer> frequencies = new HashMap<>();
        String lowerText = normaliseText(text);

        for(int i = 0; i < lowerText.length(); i++) {
            char currentChar = lowerText.charAt(i);
            if (frequencies.containsKey(currentChar))
                frequencies.put(currentChar, frequencies.get(currentChar) + 1);
            else
                frequencies.put(currentChar, 1);
        }
        return frequencies;
    }

    static HashMap<String, Integer> calculateWords(String text) {
        HashMap<String, Integer> frequencies = new HashMap<>();
        String lowerText = normaliseText(text);

        for(String word : lowerText.split(" ")) {
            if (frequencies.containsKey(word))
                frequencies.put(word, frequencies.get(word) + 1);
            else
                frequencies.put(word, 1);
        }
        return frequencies;
    }
}
