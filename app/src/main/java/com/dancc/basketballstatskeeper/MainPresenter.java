package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.List;

class MainPresenter {
  public interface Interface {
    void displayGames(List<Game> games);
  }

  private Interface page;

  void onAttachPage(Interface page) {
    this.page = page;

    page.displayGames(MockData.getMockGames());
  }

  void onAddPlayerButtonClicked(int number, String name) {

  }
}
