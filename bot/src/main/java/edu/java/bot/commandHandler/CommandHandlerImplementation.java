package edu.java.bot.commandHandler;

import edu.java.bot.chatCommand.ChatCommand;
import edu.java.bot.chatCommand.UnknownCommand;
import edu.java.bot.pojo.TgChat;
import edu.java.bot.service.ChatService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CommandHandlerImplementation implements CommandHandler {

    private final ChatService chatService;
    private final List<ChatCommand> commands;
    private final ChatCommand startCommand;
    private static final ChatCommand UNKNOWN_COMMAND = new UnknownCommand();

    public CommandHandlerImplementation(ChatService chatService, List<ChatCommand> commands, ChatCommand startCommand) {
        this.chatService = chatService;
        this.commands = commands;
        this.startCommand = startCommand;
    }

    @Override
    public ChatCommand handle(long senderId, String text) {
        Optional<TgChat> optionalPerson = chatService.getById(senderId);
        ChatCommand commandToPerform = UNKNOWN_COMMAND;
        if (optionalPerson.isPresent()) {
            TgChat sender = optionalPerson.get();
            List<ChatCommand> appropriateHandlers = new ArrayList<>();
            for (ChatCommand command : commands) {
                if (command.checkState(sender.getState())) {
                    appropriateHandlers.add(command);
                }
            }
            for (ChatCommand command : appropriateHandlers) {
                if (command.handle(text, sender)) {
                    commandToPerform = command;
                    break;
                }
            }
            chatService.save(sender);
        } else {
            if (startCommand.handle(text, null)) {
                commandToPerform = startCommand;
                chatService.save(new TgChat(senderId));
            }
        }
        return commandToPerform;
    }
}
