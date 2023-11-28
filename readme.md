# Comment Interceptor Telegram Bot

This is a simple Telegram bot built with Spring Boot that intercepts messages from a source chat and forwards them to a target chat. The application uses the Telegram Bots API and is designed to be easily configurable.

## Features

- **Message Interception**: Listens for updates from the source chat and forwards messages to the target chat.
- **Configuration**: Utilizes a configuration file (`application.properties`) to set up the bot's name and token.

## How to Use

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/yourusername/comment-interceptor-bot.git
   ```

2. **Configuration**:

   Edit the `application.properties` file in the `src/main/resources` directory with your bot's name and token:

   ```properties
   bot.name=YourBotName
   bot.token=YourBotToken
   ```

3. **Build and Run**:

   Navigate to the project root directory and build the application:

   ```bash
   mvn clean install
   ```

   Run the application:

   ```bash
   java -jar target/comment-interceptor-0.0.1-SNAPSHOT.jar
   ```

4. **Start Chat Interception**:

   Start a chat in the source chat identified by `BotConstants.SOURCE_CHAT_ID`. Messages from this chat will be intercepted and forwarded to the target chat identified by `BotConstants.TARGET_CHAT_ID`.

## Dependencies

- **Spring Boot**: Provides a simple and efficient way to set up a Java application.
- **Telegram Bots API**: Interacts with the Telegram Bots API to send and receive messages.

## Configuration Details

### `CommentInterceptorBot`

- **sourceChatId**: The chat ID of the source chat from which messages will be intercepted.
- **targetChatId**: The chat ID of the target chat to which intercepted messages will be forwarded.
- **config**: An instance of `BotConfig` containing bot configuration details.

### `BotConfig`

- **botName**: The name of your Telegram bot.
- **token**: The authentication token for your Telegram bot.

## Note

This is a basic example, and additional features or improvements can be added based on specific requirements. Refer to the [Telegram Bots API documentation](https://core.telegram.org/bots/api) for more details on available features and capabilities.