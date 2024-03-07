package edu.skku.cs.pp.squad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.Squad

class SquadListAdapter(val context: Context, val squads: List<Squad>): BaseAdapter() {

    override fun getCount(): Int {
        return squads.size
    }

    override fun getItem(p0: Int): Any {
        return squads[p0]
    }

    override fun getItemId(p0: Int): Long {
        return squads[p0].id
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.squad_entry, null)
        val data = squads[p0]

        val nameView = view.findViewById<TextView>(R.id.squad_name)
        val formationView = view.findViewById<TextView>(R.id.squad_formation)
        val fwView = view.findViewById<TextView>(R.id.squad_fw_stat)
        val mfView = view.findViewById<TextView>(R.id.squad_mf_stat)
        val dfView = view.findViewById<TextView>(R.id.squad_df_stat)

        nameView.text = data.name
        formationView.text = data.formation.name
        fwView.text = data.fwStat.toString()
        mfView.text = data.mfStat.toString()
        dfView.text = data.dfStat.toString()

        view.setOnClickListener {
            val intent = Intent(context, SquadActivity::class.java).apply {
                putExtra("squadId", data.id)
            }
            context.startActivity(intent)
        }

        return view
    }
}