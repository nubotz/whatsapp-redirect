package app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Properties;

public class WhatsappRedirectBot extends TelegramLongPollingBot {
    private static final String WHATSAPP_URL = "https://wa.me/";
    private final String token;
    private final String username;

    public WhatsappRedirectBot(Properties properties) {
        this.token = properties.getProperty("app.bot_token");
        this.username = properties.getProperty("app.bot_username");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message receivedMessage = update.getMessage();
        if (receivedMessage == null || !receivedMessage.hasText()) return;

        String text = receivedMessage.getText();

        SendMessage sendMessage = new SendMessage().setChatId(receivedMessage.getChatId());

        if (text.equals("/start")) {
            sendMessage.setText("send me the phone number");
        } else if (PhoneNumberChecker.isValidPhoneNumber(text)) {
            ReplyKeyboard inlineKeyboardMarkup = inlineKeyboardMarkup(text);
            sendMessage.setText("Whatsapp:").setReplyMarkup(inlineKeyboardMarkup);
        } else {
            return;
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard inlineKeyboardMarkup(String text) {
        String withCountryCode = withCountryCode(text);
        return new InlineKeyboardMarkup().setKeyboard(List.of(
                List.of(new InlineKeyboardButton().setText(text).setUrl(url(text))),
                List.of(new InlineKeyboardButton().setText(withCountryCode).setUrl(url(withCountryCode)))
        ));
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private String url(String content) {
        return WHATSAPP_URL + content;
    }

    private String withCountryCode(String phoneNumber) {
        return "+852" + phoneNumber;
    }
}
