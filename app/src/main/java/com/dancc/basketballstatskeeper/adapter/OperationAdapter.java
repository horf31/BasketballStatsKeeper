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
import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OperationViewHolder> {

  private List<Operation> operations;

  public OperationAdapter(List<Operation> operations) {
    this.operations = operations;
  }

  @NonNull
  @Override
  public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.operation_cell_view, parent, false);

    return new OperationViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
    holder.bind(operations.get(position));
  }

  @Override
  public int getItemCount() {
    return operations.size();
  }

  static class OperationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.playerTextView)
    TextView playerTextView;

    @BindView(R.id.operationTextView)
    TextView operationTextView;

    OperationViewHolder(View v) {
      super(v);

      ButterKnife.bind(this, v);
    }

    void bind(Operation operation) {
      playerTextView.setText(Integer.toString(operation.playerId));
      String displayText = getDisplayText(operation.action);
      operationTextView.setText(displayText);
    }

    String getDisplayText(Action action) {
      switch (action) {
        case POINT1:
          return "Made A Free Throw";
        case POINT2:
          return "Made A 2-Point Shot";
        case POINT3:
          return "Scored A 3-Point Shot";
        case REBOUND:
          return "Got 1 Rebound";
        case ASSIST:
          return "Got 1 Assist";
        case BLOCK:
          return "Got 1 Block";
        case STEAL:
          return "Got 1 Steal";
        case TURNOVER:
          return "Got 1 Turnover";
        case POINT3MISSED:
          return "Missed A 3-Point Shot";
        case POINT2MISSED:
          return "Missed A 2-Point Shot";
        case POINT1MISSED:
          return "Missed A Free Throw";
      }

      throw new RuntimeException("Unaccepted");
    }
  }
}
