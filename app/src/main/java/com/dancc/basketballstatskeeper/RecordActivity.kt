package com.dancc.basketballstatskeeper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dancc.basketballstatskeeper.RecordPresenter.Interface
import com.dancc.basketballstatskeeper.adapter.OperationAdapter
import com.dancc.basketballstatskeeper.adapter.OperationAdapter.OperationAdapterCallback
import com.dancc.basketballstatskeeper.adapter.PlayerIconAdapter
import com.dancc.basketballstatskeeper.adapter.PlayerIconAdapter.PlayerIconAdapterCallback
import com.dancc.basketballstatskeeper.db.GameDatabase
import com.dancc.basketballstatskeeper.model.Action.ASSIST
import com.dancc.basketballstatskeeper.model.Action.BLOCK
import com.dancc.basketballstatskeeper.model.Action.POINT1
import com.dancc.basketballstatskeeper.model.Action.POINT1MISSED
import com.dancc.basketballstatskeeper.model.Action.POINT2
import com.dancc.basketballstatskeeper.model.Action.POINT2MISSED
import com.dancc.basketballstatskeeper.model.Action.POINT3
import com.dancc.basketballstatskeeper.model.Action.POINT3MISSED
import com.dancc.basketballstatskeeper.model.Action.REBOUND
import com.dancc.basketballstatskeeper.model.Action.STEAL
import com.dancc.basketballstatskeeper.model.Action.TURNOVER
import com.dancc.basketballstatskeeper.model.Operation
import com.dancc.basketballstatskeeper.model.Player
import kotlinx.android.synthetic.main.activity_record.endGameButton
import kotlinx.android.synthetic.main.activity_record.lastOperationsRecycler
import kotlinx.android.synthetic.main.activity_record.playerRecycler
import kotlinx.android.synthetic.main.activity_record.undoButton
import kotlinx.android.synthetic.main.operation_panel.missOnePoint
import kotlinx.android.synthetic.main.operation_panel.missThreePoints
import kotlinx.android.synthetic.main.operation_panel.missTwoPoints
import kotlinx.android.synthetic.main.operation_panel.plusAssist
import kotlinx.android.synthetic.main.operation_panel.plusBlock
import kotlinx.android.synthetic.main.operation_panel.plusOnePoint
import kotlinx.android.synthetic.main.operation_panel.plusRebound
import kotlinx.android.synthetic.main.operation_panel.plusSteal
import kotlinx.android.synthetic.main.operation_panel.plusThreePoints
import kotlinx.android.synthetic.main.operation_panel.plusTurnover
import kotlinx.android.synthetic.main.operation_panel.plusTwoPoints
import java.util.ArrayList

class RecordActivity : AppCompatActivity(), Interface, PlayerIconAdapterCallback, OperationAdapterCallback {
  
  private lateinit var recordPresenter: RecordPresenter
  private val operationAdapter = OperationAdapter(
      ArrayList(), this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_record)

    setUpLastOperationsRecycler()
    setUpBottomButtons()

    // Set up presenter
    val application = applicationContext as CustomApplication
    recordPresenter = RecordPresenter(
        GameDatabase.getInstance(this),
        application.ioScheduler,
        application.uiScheduler
    )
    recordPresenter.onAttachPage(this)
  }

  override fun onDestroy() {
    recordPresenter.onDetachPage()
    super.onDestroy()
  }

  private fun setUpLastOperationsRecycler() {
    lastOperationsRecycler.layoutManager = LinearLayoutManager(this)
    lastOperationsRecycler.adapter = operationAdapter
  }

  // Adapter Callback
  override fun onPlayerClicked(player: Player) {
    recordPresenter.onPlayerClicked(player)
  }

  override fun onOperationRemoveIconClicked(position: Int) {
    recordPresenter.onRemoveOperationClicked(position)
  }

  // Presenter Callback
  override fun displayPlayers(players: List<Player>?) {
    playerRecycler.layoutManager = LinearLayoutManager(this)
    playerRecycler.adapter = PlayerIconAdapter(players, this)
  }

  override fun addOperation(
      operation: Operation) {
    operationAdapter.addOperation(operation)
    lastOperationsRecycler!!.smoothScrollToPosition(operationAdapter.itemCount)
  }

  override fun removeLastOperation() {
    operationAdapter.removeLastOperation()
  }

  override fun removeOperation(position: Int) {
    operationAdapter.removeOperation(position)
  }

  override fun displayMissingPlayerToast() {
    Toast.makeText(applicationContext, getText(R.string.missing_player_toast_text),
        Toast.LENGTH_SHORT).show()
  }

  override fun displayLastOperationToast() {
    Toast.makeText(applicationContext, getText(R.string.operation_empty_toast_text),
        Toast.LENGTH_SHORT).show()
  }

  override fun goToDisplayActivity(gameId: Int) {
    val intent = Intent(this, DisplayActivity::class.java)
    intent.putExtra(DisplayActivity.GAME_ID, gameId)
    startActivity(intent)
  }

  override fun setUpOperationPanel() {
    plusOnePoint.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT1)
    }
    plusTwoPoints.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT2)
    }
    plusThreePoints.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT3)
    }
    missOnePoint.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT1MISSED)
    }
    missTwoPoints.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT2MISSED)
    }
    missThreePoints.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          POINT3MISSED)
    }
    plusRebound.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          REBOUND)
    }
    plusAssist.setOnClickListener { 
      recordPresenter.onOperationActionClicked(ASSIST)
    }
    plusBlock.setOnClickListener { 
      recordPresenter.onOperationActionClicked(BLOCK)
    }
    plusSteal.setOnClickListener { 
      recordPresenter.onOperationActionClicked(STEAL)
    }
    plusTurnover.setOnClickListener { 
      recordPresenter.onOperationActionClicked(
          TURNOVER)
    }
  }

  private fun setUpBottomButtons() {
    undoButton.setOnClickListener {  recordPresenter.onUndoButtonClicked() }
    endGameButton.setOnClickListener {  recordPresenter.onEndGameButtonClicked() }
  }
}