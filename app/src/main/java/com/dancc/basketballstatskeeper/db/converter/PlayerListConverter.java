package com.dancc.basketballstatskeeper.db.converter;

import androidx.room.TypeConverter;
import com.dancc.basketballstatskeeper.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PlayerListConverter {
  private static Gson gson = new Gson();

  @TypeConverter
  public static List<Player> deserialize(String data) {
    if (data == null) {
      return Collections.emptyList();
    }
    Type listType = new TypeToken<List<Player>>() {}.getType();

    return gson.fromJson(data, listType);
  }

  @TypeConverter
  public static String serialize(List<Player> data) {
    Type listType = new TypeToken<List<Player>>() {}.getType();
    return gson.toJson(data, listType);
  }
}

