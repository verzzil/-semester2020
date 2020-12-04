package ru.itis.repositories.messages;

import ru.itis.dto.Message;
import ru.itis.repositories.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message> {
    List<Message> getDialog(Integer idFromUser, Integer idToUser);
}
