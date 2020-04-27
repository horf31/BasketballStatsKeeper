package com.dancc.basketballstatskeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.dancc.basketballstatskeeper.model.Game;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;

@Dao
public interface GameDao {
  @Query("SELECT * FROM Game")
  Maybe<List<Game>> getAll();

  @Query("SELECT * FROM Game WHERE gameId LIKE :id LIMIT 1")
  Maybe<Game> findById(int id);

  @Query("SELECT * FROM Game LIMIT 1")
  Maybe<Game> getOne();

  @Insert
  Completable insert(Game game);

  // Be CAREFUL calling it!!!
  @Query("DELETE FROM Game")
  Completable nukeTable();
}
