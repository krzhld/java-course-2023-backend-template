package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;

public interface CommandHandler {

    ChatCommand handle(long senderId, String text);
}
