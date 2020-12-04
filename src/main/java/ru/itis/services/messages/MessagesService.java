package ru.itis.services.messages;

import ru.itis.dto.Message;
import ru.itis.dto.UserDto;

import java.util.HashMap;
import java.util.List;

public interface MessagesService {
    void saveToDb(Message message);
    HashMap<Integer, List<Message>> getDialogs(Integer currentUserId, List<UserDto> chattingUsers);
}