package com.dancc.basketballstatskeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.GameAdapter.GameViewHolder
import com.dancc.basketballstatskeeper.model.Game
import kotlinx.android.synthetic.main.game_cell_view.view.gameTextView

class GameAdapter(
    private val callback: GameAdapterCallback) : Adapter<GameViewHolder>() {
  interface GameAdapterCallback {
    fun onGameClicked(game: Game?)
  }

  private val games = mutableListOf<Game>()
  fun setGames(games: List<Game>) {
    this.games.clear()
    this.games.addAll(games)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(
        layout.game_cell_view, parent, false)
    val viewHolder = GameViewHolder(v)
    v.setOnClickListener {
      callback.onGameClicked(games[viewHolder.adapterPosition])
    }
    return viewHolder
  }

  override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
    holder.bind(games[position])
  }

  override fun getItemCount(): Int {
    return games.size
  }

  class GameViewHolder(v: View) : ViewHolder(v) {
    fun bind(game: Game) {
      itemView.gameTextView.text = String.format("%d", game.gameId)
    }
  }
}