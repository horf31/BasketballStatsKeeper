package com.dancc.basketballstatskeeper.repository;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.dancc.basketballstatskeeper.model.Game;
import java.util.List;

public class GameViewModel extends AndroidViewModel {
  private GameRepository gameRepository;

  private LiveData<List<Game>> games;

  public GameViewModel(Application application) {
    super(application);

    gameRepository = new GameRepository(application);
    games = gameRepository.getGames();
  }

  public LiveData<List<Game>> getGames() {
    return games;
  }

  public LiveData<Game> getGameById(int id) {
    return gameRepository.getGameById(id);
  }

  public void insert(Game game) {
    gameRepository.insert(game);
  }
}
