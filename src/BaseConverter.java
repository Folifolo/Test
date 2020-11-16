import java.util.Arrays;
import java.util.Locale;
public interface BaseConverter {



    double convert(double celsius);

    public static BaseConverter getInstance() {
        Locale locale = Locale.getDefault(); //запомнить!
        String[] fahrenheitCountries = {"BS", "US", "BZ", "KY", "PW"};
        if (Arrays.asList(fahrenheitCountries).contains(locale.getCountry()))
            return new Fahrenheit();
        else
            return new Celsius();
    }
}

class Celsius implements   BaseConverter{

    @Override
    public double convert(double celsius) {
        return celsius;
    }
}

class Fahrenheit implements  BaseConverter{

    @Override
    public double convert(double celsius) {
        return (9.0/5.0)*celsius +32;
    }
}

class Kelvin implements BaseConverter {

    @Override
    public double convert(double celsius) {
        return celsius +273.15;
    }
}
