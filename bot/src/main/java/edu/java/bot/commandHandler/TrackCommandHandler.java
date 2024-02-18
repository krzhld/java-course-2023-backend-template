package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.TrackCommand;

public class TrackCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        if (text.equals("/track")) {
            return new TrackCommand();
        }
        return getNext().handle(text);
    }
}
