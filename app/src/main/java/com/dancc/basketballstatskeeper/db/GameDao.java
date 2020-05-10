package com.dancc.basketballstatskeeper.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.dancc.basketballstatskeeper.model.Game;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface GameDao {
  // LiveData way
  @Query("SELECT * FROM Game")
  LiveData<List<Game>> getAll();

  @Query("SELECT * FROM Game WHERE gameId LIKE :id LIMIT 1")
  LiveData<Game> findByIdLive(int id);

  // RxJava way
  @Query("SELECT * FROM Game")
  Maybe<List<Game>> getGames();

  @Query("SELECT * FROM Game")
  List<Game> getAllBlocking();

  @Query("SELECT * FROM Game WHERE gameId LIKE :id LIMIT 1")
  Maybe<Game> findById(int id);

  @Query("SELECT * FROM Game LIMIT 1")
  Maybe<Game> getOne();

  @Insert
  Single<Long> insert(Game game);

  @Insert
  Completable insertAll(List<Game> games);

  // Be CAREFUL calling it!!!
  @Query("DELETE FROM Game")
  Completable nukeTable();
}
