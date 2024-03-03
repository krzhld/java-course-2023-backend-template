package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.commandHandler.CommandHandler;
import edu.java.bot.pojo.Link;
import edu.java.bot.pojo.TgChat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommandHandlerTest {

    private final CommandHandler commandHandler;
    private final ChatService chatService;

    @Autowired
    public CommandHandlerTest(CommandHandler commandHandler, ChatService chatService) {
        this.commandHandler = commandHandler;
        this.chatService = chatService;
    }

    @Test
    public void testListMessageWhenLinkListIsEmpty() {
        long id = 1L;
        String expectedResult = "Вы пока не отслеживаете ни одной ссылки.";

        chatService.save(new TgChat(1L));
        SendMessage result = commandHandler.handle(id, "/list").getMessage(id);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText.trim()).isEqualTo(expectedResult);
    }

    @Test
    public void testListMessageWhenLinkListIsNotEmpty() {
        long id = 1L;
        String expectedResult = """
            Отслеживаемые ссылки:

            1. https://github.com/

            2. https://stackoverflow.com/""";
        TgChat tgChat = new TgChat(id);
        tgChat.getLinkList().add(new Link(0, URI.create("https://github.com/")));
        tgChat.getLinkList().add(new Link(0, URI.create("https://stackoverflow.com/")));

        chatService.save(tgChat);
        SendMessage result = commandHandler.handle(id, "/list").getMessage(id);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText.trim()).isEqualTo(expectedResult);
    }

    @Test
    public void testMessageWhenWrongInputCommand() {
        long id = 1L;
        String wrongCommand = "/unhandled command";
        TgChat tgChat = new TgChat(id);
        String expectedResult = """
            Неизвестная команда.""";

        chatService.save(tgChat);
        ChatCommand command = commandHandler.handle(id, wrongCommand);
        SendMessage result = command.getMessage(id);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText).isEqualTo(expectedResult);
    }
}
