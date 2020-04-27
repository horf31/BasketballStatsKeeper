package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dancc.basketballstatskeeper.db.converter.ListStringConverter;
import java.util.List;

@Entity
public class Game {
  @PrimaryKey(autoGenerate = true)
  public int gameId;

  @TypeConverters(ListStringConverter.class)
  public List<String> playerIds;

  @TypeConverters(ListStringConverter.class)
  public List<String> gameStatsIds;
}
