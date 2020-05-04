package com.dancc.basketballstatskeeper.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.dancc.basketballstatskeeper.model.Player;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface PlayerDao {
  @Query("SELECT * FROM Player")
  Maybe<List<Player>> getAll();

  @Query("SELECT * FROM Player WHERE id LIKE :id LIMIT 1")
  Maybe<Player> findById(int id);

  @Insert
  Single<Long> insert(Player player);

  @Insert
  Completable insertAll(List<Player> players);

  @Delete
  Completable delete(Player player);

  //    @Update
  //  //    fun update(id: Int)
  //  //

  // Be CAREFUL calling it!!!
  @Query("DELETE FROM Player")
  void nukeTable();
}
