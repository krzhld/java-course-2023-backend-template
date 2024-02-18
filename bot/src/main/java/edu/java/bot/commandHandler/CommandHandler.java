package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import lombok.Data;

@Data
public abstract class CommandHandler {
    private CommandHandler next;

    public abstract ChatCommand handle(String text);

}
