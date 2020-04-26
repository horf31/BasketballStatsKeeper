package com.dancc.basketballstatskeeper.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

  public interface PlayerIconAdapterCallback {
    void onPlayerClicked(Player player);
  }

  private List<Player> players;
  private PlayerIconAdapterCallback callback;

  private int selectedPosition = RecyclerView.NO_POSITION;

  public PlayerIconAdapter(List<Player> players, PlayerIconAdapterCallback callback) {
    this.players = players;
    this.callback = callback;
  }

  @NonNull
  @Override
  public PlayerIconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.player_icon_cell_view, parent, false);

    final PlayerIconViewHolder viewHolder = new PlayerIconViewHolder(v);
    viewHolder.itemView.setOnClickListener(view -> {
      callback.onPlayerClicked(players.get(viewHolder.getAdapterPosition()));
      notifyItemChanged(selectedPosition);
      selectedPosition = viewHolder.getAdapterPosition();
      notifyItemChanged(selectedPosition);
    });

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull PlayerIconViewHolder holder, int position) {
    holder.bind(players.get(position), selectedPosition == position);
  }

  @Override
  public int getItemCount() {
    return players.size();
  }

  static class PlayerIconViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.playerOutline)
    ImageView playerOutline;

    @BindView(R.id.playerNumber)
    TextView playerNumber;

    @BindView(R.id.playerName)
    TextView playerName;

    PlayerIconViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    @SuppressLint("DefaultLocale")
    void bind(Player player, Boolean isSelected) {
      playerNumber.setText(String.format("%d", player.number));
      playerName.setText(player.name);

      if (isSelected) {
        playerOutline.setColorFilter(
            itemView.getResources().getColor(R.color.radish));
      } else {
        playerOutline.setColorFilter(
            itemView.getResources().getColor(R.color.black20));
      }
    }
  }
}
