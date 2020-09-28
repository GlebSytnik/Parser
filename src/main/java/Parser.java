import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern patternTemperature = Pattern.compile("\\+\\d{2}");
    private static final Pattern patternTime = Pattern.compile("\\d{2}\\:\\d{2}");

    private static Document getDocument(String url) throws IOException {
        return Jsoup.parse(new URL(url), 5000);
    }

    private static String getTemperatureFromString(String temperature) {
        Matcher matcher = patternTemperature.matcher(temperature);
        if (matcher.find()) {
            return matcher.group();
        }
        return " ";
    }

    private static String getTimeTemperature(String time) {
        Matcher matcher = patternTime.matcher(time);
        if (matcher.find()) {
            return matcher.group();
        }
        return " ";
    }

    public static void main(String[] args) throws IOException {
        // System.out.println(Pattern.matches("\\d{2}\\:\\d{2}","22:23"));

        Document page = getDocument("https://www.gismeteo.ua/weather-kharkiv-5053/");
        Element tableWth = page.select("div[class = forecast_frame hw_wrap]").first();
        String temperatures = tableWth.select(" span[class = unit unit_temperature_c]").first().text();
        String temperature = getTemperatureFromString(temperatures);
        String time;
        String selectTime = tableWth.select("div[id = time]").text();
        time = getTimeTemperature(selectTime);
        System.out.println("Температура сегодня: " + temperature + "\n" + "Время: " + time);

    }
}
