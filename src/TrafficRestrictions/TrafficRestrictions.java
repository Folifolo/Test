package TrafficRestrictions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrafficRestrictions {
    final private static int DATE_FIELD1 = 10;
    final private static int DATE_FIELD2 = 11;
    final private static int DATE_LENGTH = 8;

    public static void main(String[] args) {
        String path, dateStr;
        try{
            path = args[0];
            dateStr = args[1];
        } catch (ArrayIndexOutOfBoundsException o) {
            System.out.println("You need to run program as java TrafficRestrictions \"path_to_csv\" dd.MM.yyyy");
            return;
        }
        int date = 0;
        try {
            date = strToDate(dateStr);
        } catch (WrongDate wrongDate) {
            System.out.println("You need to enter date in format dd.MM.yyyy");
            return;
        }
        int restriction = parseCsv(path, date);
        if(restriction >= 0)
            System.out.println("Traffic restrictions in force on this day : " + parseCsv(path, date));
    }

    public static int parseCsv(String filePath, int date) {
        int counter = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while((line = br.readLine() )!= null) {
                int[] dates = parseDatesInLine(line);
                if(dates[0] <= date && dates[1] >= date) {
                    counter++;
                }
            }
        } catch (IOException e) {
            System.out.println("The system cannot find the path specified");
            return -1;
        }
        return counter;
    }

    private static int strToDate(String str) throws WrongDate {
        int year, month, day;
        String[] splitedStr = str.split("\\.");
        if(splitedStr.length != 3)
            throw new ArrayIndexOutOfBoundsException();

        day = Integer.parseInt(splitedStr[0]);
        month = Integer.parseInt(splitedStr[1]);
        year = Integer.parseInt(splitedStr[2]);
        if((day < 0 || day > 31) || (month < 0 || month > 12) || (year < 0))
            throw new WrongDate();
        return day + month*100 + year*10000;
    }

    private static int[] parseDatesInLine (String line) {
        boolean inQuotes = false, isDatesFound = false;
        int[] res = new int[2];
        int counter = 0;
        int i = 0;
        char[] arr = line.toCharArray();
        while(!isDatesFound) {
            if(arr[i] == ',') {
                if(!inQuotes)
                    counter++;
            }
            else if(arr[i] == '\"')
                inQuotes = !inQuotes;
            else if(counter == DATE_FIELD1){
                res[0] = Integer.parseInt(new String(arr, i, DATE_LENGTH));
                i += DATE_LENGTH-1;
                }
            else if(counter == DATE_FIELD2) {
                res[1] = Integer.parseInt(new String(arr, i, DATE_LENGTH));
                isDatesFound = true;
                }
            i++;
        }
        return res;
    }

}

class WrongDate extends Throwable {
    String message;

    WrongDate(String message) {
        this.message = "Wrong date was entered ";
    }

    WrongDate() {
        this("");
    }

}