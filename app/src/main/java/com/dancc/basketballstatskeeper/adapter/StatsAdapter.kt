package com.dancc.basketballstatskeeper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.StatsAdapter.StatsViewHolder
import com.dancc.basketballstatskeeper.model.GameStats
import kotlinx.android.synthetic.main.stat_row_header_view.view.ast
import kotlinx.android.synthetic.main.stat_row_header_view.view.blk
import kotlinx.android.synthetic.main.stat_row_header_view.view.onePT
import kotlinx.android.synthetic.main.stat_row_header_view.view.pts
import kotlinx.android.synthetic.main.stat_row_header_view.view.reb
import kotlinx.android.synthetic.main.stat_row_header_view.view.stl
import kotlinx.android.synthetic.main.stat_row_header_view.view.threePT
import kotlinx.android.synthetic.main.stat_row_header_view.view.turnOver
import kotlinx.android.synthetic.main.stat_row_header_view.view.twoPT

class StatsAdapter : Adapter<StatsViewHolder>() {

  private var gameStats = mutableListOf<GameStats>()

  fun setGameStats(gameStats: List<GameStats>) {
    this.gameStats.clear()
    this.gameStats.addAll(gameStats)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
    val v = LayoutInflater.from(parent.context)
        .inflate(layout.stat_row_header_view, parent, false)
    return StatsViewHolder(v)
  }

  override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
    holder.bind(gameStats[position])
  }

  override fun getItemCount(): Int {
    return gameStats.size
  }

  class StatsViewHolder(v: View?) : ViewHolder(v!!) {

    fun bind(gameStats: GameStats) {
      itemView.apply {
        pts.text = String.format("%d", gameStats.points)
        reb.text = String.format("%d", gameStats.rebounds)
        ast.text = String.format("%d", gameStats.assists)
        blk.text = String.format("%d", gameStats.blocks)
        stl.text = String.format("%d", gameStats.steals)
        turnOver.text = String.format("%d", gameStats.turnovers)
        twoPT.text = String.format("%d - %d", gameStats.twoPtsMade, gameStats.twoPtsAttempted)
        threePT.text = String.format("%d - %d", gameStats.threePtsMade, gameStats.threePtsAttempted)
        onePT.text = String.format("%d - %d", gameStats.freeThrowMade, gameStats.freeThrowAttempted)
      }
    }
  }
}