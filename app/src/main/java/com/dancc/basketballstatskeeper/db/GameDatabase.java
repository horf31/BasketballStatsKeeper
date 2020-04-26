package com.dancc.basketballstatskeeper.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.dancc.basketballstatskeeper.model.Player;

@Database(entities = { Player.class }, version = 1)
public abstract class GameDatabase extends RoomDatabase {

  private static final String DB_NAME = "game-database";
  private static GameDatabase INSTANCE;

  public abstract PlayerDao playerDao();

  public static GameDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context, GameDatabase.class, DB_NAME).build();
    }
    return INSTANCE;
  }
}
