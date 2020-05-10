package com.dancc.basketballstatskeeper.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.R;
import com.dancc.basketballstatskeeper.model.GameStats;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {

  private List<GameStats> gameStats;

  public StatsAdapter() {
  }

  public void setGameStats(List<GameStats> gameStats) {
    this.gameStats = gameStats;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.stat_row_header_view, parent, false);

    return new StatsViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
    holder.bind(gameStats.get(position));
  }

  @Override
  public int getItemCount() {
    return gameStats == null ? 0 : gameStats.size();
  }

  static class StatsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.pts)
    TextView pts;

    @BindView(R.id.reb)
    TextView reb;

    @BindView(R.id.ast)
    TextView ast;

    @BindView(R.id.blk)
    TextView blk;

    @BindView(R.id.stl)
    TextView stl;

    @BindView(R.id.to)
    TextView to;

    @BindView(R.id.twoPT)
    TextView twoPT;

    @BindView(R.id.threePT)
    TextView threePT;

    @BindView(R.id.onePT)
    TextView onePT;

    StatsViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    @SuppressLint("DefaultLocale")
    void bind(GameStats gameStats) {
      pts.setText(String.format("%d", gameStats.points));
      reb.setText(String.format("%d", gameStats.rebounds));
      ast.setText(String.format("%d", gameStats.assists));
      blk.setText(String.format("%d", gameStats.blocks));
      stl.setText(String.format("%d", gameStats.steals));
      to.setText(String.format("%d", gameStats.turnovers));

      twoPT.setText(String.format("%d - %d", gameStats.twoPtsMade, gameStats.twoPtsAttempted));
      threePT.setText(
          String.format("%d - %d", gameStats.threePtsMade, gameStats.threePtsAttempted));
      onePT.setText(
          String.format("%d - %d", gameStats.freeThrowMade, gameStats.freeThrowAttempted));
    }
  }
}
