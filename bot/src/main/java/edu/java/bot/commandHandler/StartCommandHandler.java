package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.StartCommand;

public class StartCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        if (text.equals("/start")) {
            return new StartCommand();
        }
        return getNext().handle(text);
    }
}
