package com.dancc.basketballstatskeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dancc.basketballstatskeeper.R.color
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.PlayerIconAdapter.PlayerIconViewHolder
import com.dancc.basketballstatskeeper.model.Player
import kotlinx.android.synthetic.main.player_icon_cell_view.view.playerName
import kotlinx.android.synthetic.main.player_icon_cell_view.view.playerNumber
import kotlinx.android.synthetic.main.player_icon_cell_view.view.playerOutline

class PlayerIconAdapter(private val players: List<Player>?,
    private val callback: PlayerIconAdapterCallback) : Adapter<PlayerIconViewHolder>() {

  interface PlayerIconAdapterCallback {
    fun onPlayerClicked(player: Player)
  }

  private var selectedPosition = RecyclerView.NO_POSITION
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerIconViewHolder {
    val v = LayoutInflater.from(parent.context)
        .inflate(layout.player_icon_cell_view, parent, false)
    val viewHolder = PlayerIconViewHolder(v)
    viewHolder.itemView.setOnClickListener {
      callback.onPlayerClicked(players!![viewHolder.adapterPosition])
      notifyItemChanged(selectedPosition)
      selectedPosition = viewHolder.adapterPosition
      notifyItemChanged(selectedPosition)
    }
    return viewHolder
  }

  override fun onBindViewHolder(holder: PlayerIconViewHolder, position: Int) {
    holder.bind(players!![position], selectedPosition == position)
  }

  override fun getItemCount(): Int {
    return players?.size ?: 0
  }

  class PlayerIconViewHolder(v: View) : ViewHolder(v) {
    fun bind(player: Player, isSelected: Boolean) {
      itemView.apply {
        playerNumber.text = String.format("%d", player.number)
        playerName.text = player.name
        if (isSelected) {
          playerOutline.setColorFilter(itemView.resources.getColor(color.radish, null))
        } else {
          playerOutline.setColorFilter(itemView.resources.getColor(color.black20, null))
        }
      }
    }
  }
}