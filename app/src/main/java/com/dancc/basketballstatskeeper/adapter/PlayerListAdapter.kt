package com.dancc.basketballstatskeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.PlayerListAdapter.PlayerNameViewHolder
import kotlinx.android.synthetic.main.player_list_cell_view.view.playerName

class PlayerListAdapter : Adapter<PlayerNameViewHolder>() {
  private var players = mutableListOf<String>()

  fun setPlayers(players: List<String>) {
    this.players.clear()
    this.players.addAll(players)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerNameViewHolder {
    val v = LayoutInflater.from(parent.context)
        .inflate(layout.player_list_cell_view, parent, false)
    return PlayerNameViewHolder(v)
  }

  override fun onBindViewHolder(holder: PlayerNameViewHolder, position: Int) {
    if (position != 0) {
      holder.bind(players[position - 1])
    }
  }

  override fun getItemCount(): Int {
    return players.size + 1
  }

  class PlayerNameViewHolder(v: View) : ViewHolder(v) {

    fun bind(name: String?) {
      itemView.playerName.text = name
    }
  }
}