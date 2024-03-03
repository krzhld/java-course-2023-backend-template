package edu.java.bot.chatCommand;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.pojo.State;
import edu.java.bot.pojo.TgChat;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartCommand implements ChatCommand {

    private String message;

    @Override
    public boolean checkState(State state) {
        return state.equals(State.DEFAULT);
    }

    @Override
    public boolean handle(String text, TgChat sender) {
        if (Objects.equals(text, "/start")) {
            checkRegistration(sender);
            return true;
        } else {
            return false;
        }
    }

    private void checkRegistration(TgChat sender) {
        if (sender == null) {
            message = "Регистрация прошла успешно.";
        } else {
            message = "Вы уже зарегистрированы.";
        }
    }

    @Override
    public SendMessage getMessage(long receiverId) {
        return new SendMessage(receiverId, message);
    }

    @Override
    public String getDescription() {
        return "/start -- зарегистрировать пользователя";
    }
}
