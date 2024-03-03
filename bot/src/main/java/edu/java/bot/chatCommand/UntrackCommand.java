package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.Link;
import edu.java.bot.pojo.State;
import edu.java.bot.pojo.TgChat;
import java.net.URI;
import java.util.Objects;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class UntrackCommand implements ChatCommand {

    private String message;

    @Override
    public boolean checkState(State state) {
        return state.equals(State.DEFAULT) || state.equals(State.WAITING_UNTRACK);
    }

    @Override
    public boolean handle(String text, TgChat sender) {
        if (sender.getState().equals(State.WAITING_UNTRACK)) {
            return true;
        } else {
            return checkUntrackCommand(text, sender);
        }
    }

    private boolean checkUntrackCommand(String commandStr, TgChat sender) {
        if (Objects.equals(commandStr, "/untrack")) {
            sender.setState(State.WAITING_UNTRACK);
            message = """
                Введите ссылку, которую хотите прекратить отслеживать.
                Введите /cancel чтобы отменить действие.
                """;
            return true;
        } else {
            return false;
        }
    }

    private void removeUrl(String url, TgChat sender) {
        Link link = new Link(0, URI.create(url));
        sender.setState(State.DEFAULT);
        sender.getLinkList().remove(link);
        message = "Отслеживание ссылки прекращено.";
    }

    @Override
    public SendMessage getMessage(long receiverId) {
        return new SendMessage(receiverId, message);
    }

    @Override
    public String getDescription() {
        return "/untrack -- прекратить отслеживание ссылки";
    }
}
