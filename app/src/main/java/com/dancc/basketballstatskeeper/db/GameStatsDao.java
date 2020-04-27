package com.dancc.basketballstatskeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.dancc.basketballstatskeeper.model.GameStats;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;

@Dao
public interface GameStatsDao {
  @Query("SELECT * FROM GameStats")
  Maybe<List<GameStats>> getAll();

  @Insert
  Completable insertAll(List<GameStats> gameStats);

  // Be CAREFUL calling it!!!
  @Query("DELETE FROM GameStats")
  void nukeTable();
}
