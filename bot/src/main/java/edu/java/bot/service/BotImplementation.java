package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.commandHandler.CommandHandler;
import edu.java.bot.pojo.Person;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BotImplementation implements Bot {
    private final TelegramBot telegramBot;
    private final ChatService chatService;
    private final CommandHandler commandHandler;

    public BotImplementation(TelegramBot telegramBot, ChatService chatService, CommandHandler commandHandler) {
        this.telegramBot = telegramBot;
        this.chatService = chatService;
        this.commandHandler = commandHandler;
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            Message message = update.message();
            String text = message.text().trim();
            ChatCommand command = commandHandler.handle(text);
            Person person = new Person(message.chat().id());
            SendMessage request = command.getMessage(person, chatService);
            execute(request);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        SetMyCommands commands = new SetMyCommands(menuCommandArray());
        execute(commands);
        telegramBot.setUpdatesListener(
            this,
            e -> {
                if (e.response() != null) {
                    e.response().errorCode();
                    e.response().description();
                } else {
                    log.error(e.toString());
                }
            }
        );
    }

    public BotCommand[] menuCommandArray() {
        return new BotCommand[] {
            new BotCommand("/start", "зарегистрировать пользователя"),
            new BotCommand("/help", "вывести окно с командами"),
            new BotCommand("/track", "начать отслеживание ссылки"),
            new BotCommand("/untrack", "прекратить отслеживание ссылки"),
            new BotCommand("/list", "показать список отслеживаемых ссылок")
        };
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }
}
