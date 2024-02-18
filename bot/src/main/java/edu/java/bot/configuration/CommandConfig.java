package edu.java.bot.configuration;

import edu.java.bot.commandHandler.CommandHandler;
import edu.java.bot.commandHandler.HelpCommandHandler;
import edu.java.bot.commandHandler.ListCommandHandler;
import edu.java.bot.commandHandler.StartCommandHandler;
import edu.java.bot.commandHandler.TrackCommandHandler;
import edu.java.bot.commandHandler.UnknownCommandHandler;
import edu.java.bot.commandHandler.UntrackCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfig {
    @Bean
    public CommandHandler commandHandler() {
        CommandHandler start = new StartCommandHandler();
        CommandHandler help = new HelpCommandHandler();
        CommandHandler list = new ListCommandHandler();
        CommandHandler track = new TrackCommandHandler();
        CommandHandler untrack = new UntrackCommandHandler();
        CommandHandler unknown = new UnknownCommandHandler();
        start.setNext(help);
        help.setNext(list);
        list.setNext(track);
        track.setNext(untrack);
        untrack.setNext(unknown);
        return start;
    }
}
