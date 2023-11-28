package telegram.commentInterceptor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotConstants {
    private static final Properties properties = new Properties();

    static {
	try (InputStream inputStream = BotConstants.class.getClassLoader().getResourceAsStream("bot.properties")) {
	    properties.load(inputStream);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static final Long TARGET_CHAT_ID = Long.parseLong(properties.getProperty("target.id"));
    public static final Long SOURCE_CHAT_ID = Long.parseLong(properties.getProperty("source.chat.id"));
}
