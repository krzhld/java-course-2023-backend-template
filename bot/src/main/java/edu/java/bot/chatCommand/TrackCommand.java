package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Person;
import edu.java.bot.service.ChatService;
import lombok.extern.java.Log;

@Log
public class TrackCommand implements ChatCommand {
    private static final String TRACK_REQUEST = "Введите ссылку, которую хотите начать отслеживать.";

    @Override
    public SendMessage getMessage(Person person, ChatService service) {
        log.info("Запрос на добавление ссылки от: " + person.getId());
        return new SendMessage(person.getId(), TRACK_REQUEST);
    }
}
