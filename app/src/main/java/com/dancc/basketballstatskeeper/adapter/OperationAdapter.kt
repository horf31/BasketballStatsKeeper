package com.dancc.basketballstatskeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.OperationAdapter.OperationViewHolder
import com.dancc.basketballstatskeeper.model.Action
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
import kotlinx.android.synthetic.main.operation_cell_view.view.operationRemove
import kotlinx.android.synthetic.main.operation_cell_view.view.operationTextView
import kotlinx.android.synthetic.main.operation_cell_view.view.playerTextView

class OperationAdapter(
    private val operations: MutableList<Operation>,
    private val callback: OperationAdapterCallback) : Adapter<OperationViewHolder>() {
  interface OperationAdapterCallback {
    fun onOperationRemoveIconClicked(position: Int)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
    val v = LayoutInflater.from(parent.context)
        .inflate(layout.operation_cell_view, parent, false)
    val viewHolder = OperationViewHolder(v)
    viewHolder.itemView.operationRemove.setOnClickListener {
      callback.onOperationRemoveIconClicked(viewHolder.adapterPosition)
    }
    return viewHolder
  }

  fun addOperation(operation: Operation) {
    operations.add(operation)
    notifyItemInserted(operations.size - 1)
  }

  fun removeLastOperation() {
    operations.removeAt(operations.size - 1)
    notifyItemRemoved(operations.size)
  }

  fun removeOperation(position: Int) {
    operations.removeAt(position)
    notifyItemRemoved(position)
  }

  override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
    holder.bind(operations[position])
  }

  override fun getItemCount(): Int {
    return operations.size
  }

  class OperationViewHolder(v: View) : ViewHolder(v) {

    fun bind(operation: Operation) {
      itemView.playerTextView.text = operation.player.number.toString()
      val displayText = getDisplayText(operation.action)
      itemView.operationTextView.text = displayText
    }

    private fun getDisplayText(action: Action?): String {
      when (action) {
        POINT1 -> return "Made A Free Throw"
        POINT2 -> return "Made A 2-Point Shot"
        POINT3 -> return "Scored A 3-Point Shot"
        REBOUND -> return "Got 1 Rebound"
        ASSIST -> return "Got 1 Assist"
        BLOCK -> return "Got 1 Block"
        STEAL -> return "Got 1 Steal"
        TURNOVER -> return "Got 1 Turnover"
        POINT3MISSED -> return "Missed A 3-Point Shot"
        POINT2MISSED -> return "Missed A 2-Point Shot"
        POINT1MISSED -> return "Missed A Free Throw"
      }
      throw RuntimeException("Unaccepted")
    }
  }

}