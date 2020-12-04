package ru.itis.dto.coders;

import com.google.gson.*;
import ru.itis.dto.Message;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer<Message> {
    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return Message.builder()
                .fromUserId(jsonObject.get("fromUserId").getAsInt())
                .toUserId(jsonObject.get("toUserId").getAsInt())
                .text(jsonObject.get("text").getAsString())
                .build();
    }
}
