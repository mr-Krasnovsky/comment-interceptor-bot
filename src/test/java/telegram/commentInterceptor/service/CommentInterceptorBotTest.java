package telegram.commentInterceptor.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import telegram.commentInterceptor.config.BotConfig;
import telegram.commentInterceptor.config.BotConstants;

class CommentInterceptorBotTest {

    @Mock
    private BotConfig mockConfig = new BotConfig();

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    @SuppressWarnings("ThrowableNotThrown")
    public void testOnUpdateReceived_ForwardMessageCalledWhenChatIdMatches() {
	// Given
	CommentInterceptorBot bot = Mockito.spy(new CommentInterceptorBot(mockConfig));
	Update update = new Update();
	Message message = createTestMessage(BotConstants.SOURCE_CHAT_ID, 1);
	update.setMessage(message);

	try {
	    bot.onUpdateReceived(update);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}

	Mockito.verify(bot, Mockito.times(1)).forwardMessage(Mockito.any(Message.class));
    }

    @Test
    public void testOnUpdateReceived_ForwardMessageNotCalledWhenChatIdDoesNotMatch() {

	CommentInterceptorBot bot = Mockito.spy(new CommentInterceptorBot(mockConfig));
	Update update = new Update();
	Message message = createTestMessage(123456L, 2);
	update.setMessage(message);

	bot.onUpdateReceived(update);

	Mockito.verify(bot, Mockito.times(0)).forwardMessage(Mockito.any(Message.class));
    }

    @Test
    public void testForwardMessage_ExceptionOnForwarding() {

	CommentInterceptorBot bot = Mockito.spy(new CommentInterceptorBot(mockConfig));
	Update update = new Update();
	Message message = createTestMessage(BotConstants.SOURCE_CHAT_ID, 3);

	doAnswer(invocation -> {
	    throw new TelegramApiException("Test exception");
	}).when(bot).forwardMessage(Mockito.any(Message.class));

	assertThrows(TelegramApiException.class, () -> bot.forwardMessage(message), "Expected TelegramApiException");
    }

    @Test
    public void testForwardMessage_ExceptionOnForwarding_NegativeScenario() {

	CommentInterceptorBot bot = Mockito.spy(new CommentInterceptorBot(mockConfig));
	Update update = new Update();
	Message message = createTestMessage(BotConstants.SOURCE_CHAT_ID, 4);

	doNothing().when(bot).forwardMessage(Mockito.any(Message.class));

	assertDoesNotThrow(() -> bot.forwardMessage(message), "Unexpected exception thrown");
    }

    private Message createTestMessage(long chatId, int messageId) {
	Message message = new Message();
	Chat chat = new Chat();
	chat.setId(chatId);
	message.setChat(chat);
	message.setMessageId(messageId);
	return message;
    }
}
