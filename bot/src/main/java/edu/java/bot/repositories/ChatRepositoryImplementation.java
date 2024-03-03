package edu.java.bot.repositories;

import edu.java.bot.pojo.TgChat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepositoryImplementation implements ChatRepository {

    Map<Long, TgChat> storage = new HashMap<>();

    @Override
    public void save(TgChat tgChat) {
        if (tgChat != null) {
            storage.put(tgChat.getId(), tgChat);
        }
    }

    @Override
    public Optional<TgChat> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }
}
