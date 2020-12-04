package ru.itis.services.messages;

import ru.itis.dto.Message;
import ru.itis.dto.UserDto;
import ru.itis.repositories.messages.MessagesRepository;

import java.util.HashMap;
import java.util.List;

public class MessagesServiceImpl implements MessagesService {

    private MessagesRepository messagesRepository;

    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void saveToDb(Message message) {
        messagesRepository.save(message);
    }

    @Override
    public HashMap<Integer, List<Message>> getDialogs(Integer currentUserId, List<UserDto> chattingUsers) {
        HashMap<Integer, List<Message>> dialogs = new HashMap<>();
        for(UserDto user : chattingUsers) {
            dialogs.put(
                    user.getId(),
                    messagesRepository.getDialog(currentUserId, user.getId())
            );
        }
        return dialogs;
    }
}
