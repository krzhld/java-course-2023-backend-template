package edu.java.bot.service;

import edu.java.bot.pojo.TgChat;
import edu.java.bot.repositories.ChatRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository repository;

    public ChatService(ChatRepository repository) {
        this.repository = repository;
    }

    public void save(TgChat tgChat) {
        repository.save(tgChat);
    }

    public Optional<TgChat> getById(long id) {
        return repository.findById(id);
    }
}
