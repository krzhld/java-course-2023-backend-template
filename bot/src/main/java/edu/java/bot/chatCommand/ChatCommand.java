package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Person;
import edu.java.bot.service.ChatService;

public interface ChatCommand {
    SendMessage getMessage(Person person, ChatService service);
}
