package ru.itis.dto.coders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.itis.dto.Message;

import java.lang.reflect.Type;

public class MessageSerializer implements JsonSerializer<Message> {
    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fromUserId", message.getFromUserId());
        jsonObject.addProperty("toUserId", message.getToUserId());
        jsonObject.addProperty("text", message.getText());
        return jsonObject;
    }
}
