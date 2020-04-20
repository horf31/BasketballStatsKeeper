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
import com.dancc.basketballstatskeeper.model.Player;
import java.util.List;

public class PlayerIconAdapter
    extends RecyclerView.Adapter<PlayerIconAdapter.PlayerIconViewHolder> {

  private List<Player> players;

  public PlayerIconAdapter(List<Player> players) {
    this.players = players;
  }

  @NonNull
  @Override
  public PlayerIconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.player_icon_cell_view, parent, false);

    return new PlayerIconViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull PlayerIconViewHolder holder, int position) {
    holder.bind(players.get(position));
  }

  @Override
  public int getItemCount() {
    return players.size();
  }

  static class PlayerIconViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.playerNumber)
    TextView playerNumber;

    @BindView(R.id.playerName)
    TextView playerName;

    PlayerIconViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    void bind(Player player) {
      playerNumber.setText(Integer.toString(player.number));
      playerName.setText(player.name);
    }
  }
}
