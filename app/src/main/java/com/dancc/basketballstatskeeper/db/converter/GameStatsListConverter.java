package com.dancc.basketballstatskeeper.db.converter;

import androidx.room.TypeConverter;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class GameStatsListConverter {
  private static Gson gson = new Gson();

  @TypeConverter
  public static List<GameStats> deserialize(String data) {
    if (data == null) {
      return Collections.emptyList();
    }
    Type listType = new TypeToken<List<GameStats>>() {}.getType();

    return gson.fromJson(data, listType);
  }

  @TypeConverter
  public static String serialize(List<GameStats> data) {
    Type listType = new TypeToken<List<GameStats>>() {}.getType();

    return gson.toJson(data, listType);
  }
}

