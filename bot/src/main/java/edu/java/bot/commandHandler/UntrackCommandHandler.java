package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.UntrackCommand;

public class UntrackCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        if (text.equals("/untrack")) {
            return new UntrackCommand();
        }
        return getNext().handle(text);
    }
}
