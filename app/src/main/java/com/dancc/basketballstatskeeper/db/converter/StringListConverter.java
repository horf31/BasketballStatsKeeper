package com.dancc.basketballstatskeeper.db.converter;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StringListConverter {
  private static Gson gson = new Gson();

  @TypeConverter
  public static List<String> deserialize(String data) {
    if (data == null) {
      return Collections.emptyList();
    }
    Type listType = new TypeToken<List<String>>() {}.getType();

    return gson.fromJson(data, listType);
  }

  @TypeConverter
  public static String serialize(List<String> data) {
    return gson.toJson(data);
  }
}
