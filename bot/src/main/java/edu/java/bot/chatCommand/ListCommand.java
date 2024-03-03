package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Link;
import edu.java.bot.pojo.State;
import edu.java.bot.pojo.TgChat;
import java.util.List;
import java.util.Objects;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class ListCommand implements ChatCommand {

    private List<Link> linkList;

    @Override
    public boolean checkState(State state) {
        return state.equals(State.DEFAULT);
    }

    @Override
    public boolean handle(String text, TgChat sender) {
        if (Objects.equals(text, "/list")) {
            linkList = sender.getLinkList();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SendMessage getMessage(long receiverId) {
        if (linkList.isEmpty()) {
            return new SendMessage(receiverId, "Вы пока не отслеживаете ни одной ссылки.");
        } else {
            return new SendMessage(receiverId, formatOutput());
        }
    }

    private String formatOutput() {
        StringBuilder sb = new StringBuilder("Отслеживаемые ссылки:\n\n");
        for (int i = 1; i <= linkList.size(); i++) {
            sb.append(i).append(". ").append(linkList.get(i - 1)).append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "/list -- показать список отслеживаемых ссылок";
    }
}
