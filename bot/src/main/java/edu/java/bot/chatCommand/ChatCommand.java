package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.State;
import edu.java.bot.pojo.TgChat;

public interface ChatCommand {
    boolean checkState(State state);

    boolean handle(String text, TgChat sender);

    SendMessage getMessage(long receiverId);

    String getDescription();
}
