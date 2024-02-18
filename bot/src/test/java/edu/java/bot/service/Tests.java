package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.ListCommand;
import edu.java.bot.commandHandler.CommandHandler;
import edu.java.bot.commandHandler.HelpCommandHandler;
import edu.java.bot.commandHandler.ListCommandHandler;
import edu.java.bot.commandHandler.StartCommandHandler;
import edu.java.bot.commandHandler.TrackCommandHandler;
import edu.java.bot.commandHandler.UnknownCommandHandler;
import edu.java.bot.commandHandler.UntrackCommandHandler;
import edu.java.bot.pojo.Person;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Tests {
    public static CommandHandler handler() {
        CommandHandler start = new StartCommandHandler();
        CommandHandler help = new HelpCommandHandler();
        CommandHandler list = new ListCommandHandler();
        CommandHandler track = new TrackCommandHandler();
        CommandHandler untrack = new UntrackCommandHandler();
        CommandHandler unknown = new UnknownCommandHandler();
        start.setNext(help);
        help.setNext(list);
        list.setNext(track);
        track.setNext(untrack);
        untrack.setNext(unknown);
        return start;
    }

    @Test
    public void testListMessageWhenLinkListIsEmpty() {
        String expectedResult = "Вы не отслеживаете ни одной ссылки.";
        Person person = new Person(1L);

        SendMessage result = new ListCommand().getMessage(person, null);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText.trim()).isEqualTo(expectedResult);
    }

    @Test
    public void testListMessageWhenLinkListIsNotEmpty() {
        String expectedResult = """
            Отслеживаемые ссылки:
            https://github.com/
            https://stackoverflow.com/""";
        Person person = new Person(1L);
        person.getLinkList().add("https://github.com/");
        person.getLinkList().add("https://stackoverflow.com/");

        SendMessage result = new ListCommand().getMessage(person, null);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText.trim()).isEqualTo(expectedResult);
    }

    @Test
    public void testMessageWhenWrongInputCommand() {
        String wrongCommand = "/someCommand";
        Person person = new Person(1L);
        String expectedResult = """
            Неизвестная команда.
            """;
        CommandHandler handler = handler();

        ChatCommand command = handler.handle(wrongCommand);
        SendMessage result = command.getMessage(person, null);
        String resultText = (String) result.getParameters().get("text");

        assertThat(resultText).isEqualTo(expectedResult);
    }
}
