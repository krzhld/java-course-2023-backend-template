package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.UnknownCommand;

public class UnknownCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        return new UnknownCommand();
    }
}
