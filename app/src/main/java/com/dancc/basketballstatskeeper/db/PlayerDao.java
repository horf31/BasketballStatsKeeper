package com.dancc.basketballstatskeeper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.dancc.basketballstatskeeper.model.Player;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;

@Dao
public interface PlayerDao {
  @Query("SELECT * FROM Player")
  Maybe<List<Player>> getAll();

  @Query("SELECT * FROM Player WHERE id LIKE :id LIMIT 1")
  Player findById(int id);

  @Insert
  Completable insert(Player player);

  @Insert
  void insertAll(List<Player> players);

  //    @Update
  //    fun update(id: Int)
  //
  //    @Delete
  //    fun delete(id: Int)
}
