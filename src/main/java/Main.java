import app.WhatsappRedirectBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new WhatsappRedirectBot(properties()));
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties properties() throws IOException {
        var properties = new Properties();
        // load a properties file
        properties.load(Main.class.getClassLoader().getResourceAsStream("app.properties"));
        return properties;
    }
}
