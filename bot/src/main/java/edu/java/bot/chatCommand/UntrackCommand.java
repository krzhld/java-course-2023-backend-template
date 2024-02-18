package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Person;
import edu.java.bot.service.ChatService;
import lombok.extern.java.Log;

@Log
public class UntrackCommand implements ChatCommand {
    private static final String UNTRACK_REQUEST = "Введите ссылку, которую хотите перестать отслеживать.";

    @Override
    public SendMessage getMessage(Person person, ChatService service) {
        log.info("Запрос на прекращение отслеживания ссылки от: " + person.getId());
        return new SendMessage(person.getId(), UNTRACK_REQUEST);
    }
}
