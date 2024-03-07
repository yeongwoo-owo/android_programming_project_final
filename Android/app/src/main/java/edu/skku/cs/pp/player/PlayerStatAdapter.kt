package edu.skku.cs.pp.player

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import edu.skku.cs.pp.R

class PlayerStatAdapter(val context: Context, val stats: List<Pair<String, Int>>): BaseAdapter() {

    override fun getCount(): Int {
        return stats.size
    }

    override fun getItem(p0: Int): Any {
        return stats[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.player_stat_entry, null)

        val data = stats[p0]
        val statNameView = view.findViewById<TextView>(R.id.player_stat_name)
        val statValueView = view.findViewById<TextView>(R.id.player_stat_value)

        statNameView.text = data.first
        statValueView.text = data.second.toString()

        return view
    }
}