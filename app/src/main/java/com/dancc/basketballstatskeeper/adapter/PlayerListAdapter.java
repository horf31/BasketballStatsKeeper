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
import java.util.List;

public class PlayerListAdapter
    extends RecyclerView.Adapter<PlayerListAdapter.PlayerNameViewHolder> {

  private List<String> players;

  public PlayerListAdapter(List<String> players) {
    this.players = players;
  }

  @NonNull
  @Override
  public PlayerNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.player_list_cell_view, parent, false);

    return new PlayerNameViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull PlayerNameViewHolder holder, int position) {
    holder.bind(players.get(position));
  }

  @Override
  public int getItemCount() {
    return players.size();
  }

  static class PlayerNameViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.playerName)
    TextView playerName;


    PlayerNameViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    void bind(String name) {
      playerName.setText(name);
    }
  }
}
