package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Person;
import edu.java.bot.service.ChatService;
import java.util.List;
import lombok.extern.java.Log;

@Log
public class ListCommand implements ChatCommand {
    private static final String EMPTY_LINK_LIST = """
        Вы не отслеживаете ни одной ссылки.
        """;
    private static final String FOLLOWED_LINKS = """
        Отслеживаемые ссылки:
        """;

    @Override
    public SendMessage getMessage(Person person, ChatService service) {
        List<String> linkList = person.getLinkList();
        if (linkList.isEmpty()) {
            log.info("Вывели пустой список ссылок.");
            return new SendMessage(person.getId(), EMPTY_LINK_LIST);
        } else {
            log.info("Вывели отслеживаемые ссылки.");
            return new SendMessage(
                person.getId(),
                FOLLOWED_LINKS + String.join("\n", linkList)
            );
        }
    }
}
