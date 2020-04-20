package com.dancc.basketballstatskeeper;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.OperationAdapter;
import com.dancc.basketballstatskeeper.adapter.PlayerIconAdapter;
import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;

public class RecordActivity extends AppCompatActivity
    implements RecordPresenter.Interface, PlayerIconAdapter.PlayerIconAdapterCallback {

  // Recycler Displays
  @BindView(R.id.lastOperationsRecycler)
  RecyclerView lastOperationsRecycler;

  @BindView(R.id.playerRecycler)
  RecyclerView playerRecycler;

  // Operation action panel
  @BindView(R.id.plusOnePoint)
  AppCompatButton plusOnePoint;

  @BindView(R.id.plusTwoPoints)
  AppCompatButton plusTwoPoints;

  @BindView(R.id.plusThreePoints)
  AppCompatButton plusThreePoints;

  @BindView(R.id.missOnePoint)
  AppCompatButton missOnePoint;

  @BindView(R.id.missTwoPoints)
  AppCompatButton missTwoPoints;

  @BindView(R.id.missThreePoints)
  AppCompatButton missThreePoints;

  @BindView(R.id.plusRebound)
  AppCompatButton plusRebound;

  @BindView(R.id.plusAssist)
  AppCompatButton plusAssist;

  @BindView(R.id.plusBlock)
  AppCompatButton plusBlock;

  @BindView(R.id.plusSteal)
  AppCompatButton plusSteal;

  @BindView(R.id.plusTurnover)
  AppCompatButton plusTurnover;

  // Bottom buttons
  @BindView(R.id.undoButton)
  AppCompatButton undoButton;

  @BindView(R.id.endGameButton)
  AppCompatButton endGameButton;

  private RecordPresenter recordPresenter = new RecordPresenter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record);

    ButterKnife.bind(this);

    setUpLastOperationsRecycler();

    setUpPlayerRecycler();

    setUpOperationPanel();

    setUpBottomButtons();

    recordPresenter.onAttachPage(this);
  }

  private void setUpLastOperationsRecycler() {
    lastOperationsRecycler.setLayoutManager(new LinearLayoutManager(this));
    lastOperationsRecycler.setAdapter(new OperationAdapter(MockData.getMockOperations()));
  }

  private void setUpPlayerRecycler() {
    playerRecycler.setLayoutManager(new LinearLayoutManager(this));
    playerRecycler.setAdapter(new PlayerIconAdapter(MockData.getMockPlayers(), this));
  }

  // Adapter Callback
  @Override
  public void onPlayerClicked(Player player) {
    recordPresenter.onPlayerClicked(player);
  }

  // Presenter Callback
  @Override
  public void updateOperation() {

  }

  @Override
  public void displayMissingPlayerToast() {
    Toast.makeText(getApplicationContext(), getText(R.string.missing_player_toast_text),
        Toast.LENGTH_SHORT).show();
  }

  private void setUpOperationPanel() {
    plusOnePoint.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT1));

    plusTwoPoints.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT2));

    plusThreePoints.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT3));

    missOnePoint.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT1MISSED));

    missTwoPoints.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT2MISSED));

    missThreePoints.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.POINT3MISSED));

    plusRebound.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.REBOUND));

    plusAssist.setOnClickListener(view -> recordPresenter.onOperationActionClicked(Action.ASSIST));

    plusBlock.setOnClickListener(view -> recordPresenter.onOperationActionClicked(Action.BLOCK));

    plusSteal.setOnClickListener(view -> recordPresenter.onOperationActionClicked(Action.STEAL));

    plusTurnover.setOnClickListener(
        view -> recordPresenter.onOperationActionClicked(Action.TURNOVER));
  }

  private void setUpBottomButtons() {
    undoButton.setOnClickListener(view -> recordPresenter.onUndoButtonClicked());

    endGameButton.setOnClickListener(view -> recordPresenter.onEndGameButtonClicked());
  }
}
