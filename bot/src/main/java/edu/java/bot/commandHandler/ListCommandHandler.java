package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.ListCommand;

public class ListCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        if (text.equals("/list")) {
            return new ListCommand();
        }
        return getNext().handle(text);
    }
}
