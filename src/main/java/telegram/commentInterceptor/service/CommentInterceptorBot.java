package telegram.commentInterceptor.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import telegram.commentInterceptor.config.BotConfig;
import telegram.commentInterceptor.config.BotConstants;

@Getter
@Component
public class CommentInterceptorBot extends TelegramLongPollingBot {

    private final long sourceChatId = BotConstants.SOURCE_CHAT_ID;
    private final long targetChatId = BotConstants.TARGET_CHAT_ID;
    private final BotConfig config;

    public CommentInterceptorBot(BotConfig config) {
	this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
	if (update.hasChannelPost()) {
	    Message channelPost = update.getChannelPost();
	    forwardMessage(channelPost);
	}
	if (update.hasMessage()) {
	    Message message = update.getMessage();
	    if (message.getChatId().equals(sourceChatId)) {
		forwardMessage(message);
	    }
	}
    }

    public void forwardMessage(Message message) {
	ForwardMessage forwardMessage = new ForwardMessage();
	forwardMessage.setChatId(targetChatId);
	forwardMessage.setFromChatId(sourceChatId);
	forwardMessage.setMessageId(message.getMessageId());

	try {
	    execute(forwardMessage);
	} catch (TelegramApiException e) {
	    System.out.println("не удалось отправить сообщение - " + forwardMessage.getMessageId());
	}
    }

    @Override
    public String getBotUsername() {
	return config.getBotName();
    }

    @Override
    public String getBotToken() {

	return config.getToken();
    }

}
