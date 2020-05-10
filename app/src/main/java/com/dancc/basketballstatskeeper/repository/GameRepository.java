package com.dancc.basketballstatskeeper.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.dancc.basketballstatskeeper.db.GameDao;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import java.util.List;

public class GameRepository {
  private GameDao gameDao;
  private LiveData<List<Game>> games;

  GameRepository(Application application) {
    GameDatabase db = GameDatabase.getInstance(application);
    gameDao = db.gameDao();
    games = gameDao.getAll();
  }

  LiveData<List<Game>> getGames() {
    return games;
  }

  public void insert (Game game) {
    new insertAsyncTask(gameDao).execute(game);
  }

  private static class insertAsyncTask extends AsyncTask<Game, Void, Void> {

    private GameDao mAsyncTaskDao;

    insertAsyncTask(GameDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Game... params) {
      mAsyncTaskDao.insert(params[0]);
      return null;
    }
  }
}
