package com.dancc.basketballstatskeeper;

import android.content.Intent;
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
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity
    implements RecordPresenter.Interface, PlayerIconAdapter.PlayerIconAdapterCallback,
    OperationAdapter.OperationAdapterCallback {

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

  private RecordPresenter recordPresenter;

  private OperationAdapter operationAdapter =
      new OperationAdapter(new ArrayList<>(), this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record);

    ButterKnife.bind(this);

    setUpLastOperationsRecycler();

    setUpOperationPanel();

    setUpBottomButtons();

    // Set up presenter
    CustomApplication application = (CustomApplication) getApplicationContext();

    recordPresenter = new RecordPresenter(
        GameDatabase.getInstance(this),
        application.ioScheduler,
        application.uiScheduler
    );

    recordPresenter.onAttachPage(this);
  }

  @Override
  protected void onDestroy() {
    recordPresenter.onDetachPage();
    super.onDestroy();
  }

  private void setUpLastOperationsRecycler() {
    lastOperationsRecycler.setLayoutManager(new LinearLayoutManager(this));
    lastOperationsRecycler.setAdapter(operationAdapter);
  }

  // Adapter Callback
  @Override
  public void onPlayerClicked(Player player) {
    recordPresenter.onPlayerClicked(player);
  }

  @Override
  public void onOperationRemoveIconClicked(int position) {
    recordPresenter.onRemoveOperationClicked(position);
  }

  // Presenter Callback

  @Override
  public void displayPlayers(List<Player> players) {
    playerRecycler.setLayoutManager(new LinearLayoutManager(this));
    playerRecycler.setAdapter(new PlayerIconAdapter(players, this));
  }

  @Override
  public void addOperation(Operation operation) {
    operationAdapter.addOperation(operation);

    lastOperationsRecycler.smoothScrollToPosition(operationAdapter.getItemCount());
  }

  @Override
  public void removeLastOperation() {
    operationAdapter.removeLastOperation();
  }

  @Override
  public void removeOperation(int position) {
    operationAdapter.removeOperation(position);
  }

  @Override
  public void displayMissingPlayerToast() {
    Toast.makeText(getApplicationContext(), getText(R.string.missing_player_toast_text),
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void displayLastOperationToast() {
    Toast.makeText(getApplicationContext(), getText(R.string.operation_empty_toast_text),
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void goToDisplayActivity(int gameId) {
    Intent intent = new Intent(this, DisplayActivity.class);
    intent.putExtra(DisplayActivity.GAME_ID, gameId);
    startActivity(intent);
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
