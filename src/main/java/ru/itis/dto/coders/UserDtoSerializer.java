package ru.itis.dto.coders;

import com.google.gson.*;
import ru.itis.dto.UserDto;

import java.lang.reflect.Type;


public class UserDtoSerializer implements JsonSerializer<UserDto> {
    @Override
    public JsonElement serialize(UserDto userDto, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", userDto.getId());
        jsonObject.addProperty("firstName", userDto.getFirstName());
        jsonObject.addProperty("lastName", userDto.getLastName());
        jsonObject.addProperty("gender", userDto.getGender());
        jsonObject.addProperty("shortAbout", userDto.getShortAbout() != null ? userDto.getShortAbout() : "");
        jsonObject.addProperty("fullAbout", userDto.getFullAbout() != null ? userDto.getFullAbout() : "");
        jsonObject.addProperty("city", userDto.getCity());
        jsonObject.addProperty("countLikes", userDto.getCountLikes());
        jsonObject.addProperty("age", userDto.getAge() != null ? userDto.getAge() : -1);
        return jsonObject;
    }
}
