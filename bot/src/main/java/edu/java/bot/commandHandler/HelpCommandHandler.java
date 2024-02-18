package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.HelpCommand;

public class HelpCommandHandler extends CommandHandler {
    @Override
    public ChatCommand handle(String text) {
        if (text.equals("/help")) {
            return new HelpCommand();
        }
        return getNext().handle(text);
    }
}
