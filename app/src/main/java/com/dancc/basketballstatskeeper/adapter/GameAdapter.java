package com.dancc.basketballstatskeeper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.R;
import com.dancc.basketballstatskeeper.model.Game;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

  public interface GameAdapterCallback {
    void onGameClicked(Game game);
  }

  private List<Game> games;

  private GameAdapterCallback callback;

  public GameAdapter(GameAdapterCallback callback) {
    this.callback = callback;
  }

  public void setGames(List<Game> games) {
    this.games = games;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.game_cell_view, parent, false);

    GameViewHolder viewHolder = new GameViewHolder(v);

    v.setOnClickListener(
        view -> callback.onGameClicked(games.get(viewHolder.getAdapterPosition())));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
    holder.bind(games.get(position));
  }

  @Override
  public int getItemCount() {
    return games == null ? 0 : games.size();
  }

  static class GameViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.gameTextView)
    TextView gameTextView;

    GameViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    void bind(Game game) {
      gameTextView.setText(String.format("%d", game.gameId));
    }
  }
}
