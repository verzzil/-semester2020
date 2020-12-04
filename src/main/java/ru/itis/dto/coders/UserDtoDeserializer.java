package ru.itis.dto.coders;

import com.google.gson.*;
import org.springframework.core.serializer.Deserializer;
import ru.itis.dto.UserDto;

import java.lang.reflect.Type;

public class UserDtoDeserializer implements JsonDeserializer<UserDto> {
    @Override
    public UserDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return UserDto.builder()
                .id(jsonObject.get("id").getAsInt())
                .firstName(jsonObject.get("firstName").getAsString())
                .lastName(jsonObject.get("lastName").getAsString())
                .gender(jsonObject.get("gender").getAsString())
                .shortAbout(jsonObject.get("shortAbout").getAsString())
                .fullAbout(jsonObject.get("fullAbout").getAsString())
                .city(jsonObject.get("city").getAsString())
                .countLikes(jsonObject.get("countLikes").getAsInt())
                .age(jsonObject.get("age").getAsInt())
                .build();
    }
}
