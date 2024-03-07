package edu.skku.cs.pp.player

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.Player
import edu.skku.cs.pp.repository.PlayerRepository
import edu.skku.cs.pp.repository.SeasonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL

class PlayerSearchListAdapter(val context: Context, val players: List<Player>, val squadId: Long, val position: String): BaseAdapter() {

    override fun getCount(): Int {
        return players.size
    }

    override fun getItem(p0: Int): Any {
        return players[p0]
    }

    override fun getItemId(p0: Int): Long {
        return players[p0].id
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.player_search_entry, null)
        val imageView = view.findViewById<ImageView>(R.id.player_search_image)
        val seasonView = view.findViewById<ImageView>(R.id.player_search_entry_season)
        val nameView = view.findViewById<TextView>(R.id.player_search_entry_name)
        val payView = view.findViewById<TextView>(R.id.player_search_entry_pay)
        val preferView = view.findViewById<TextView>(R.id.player_search_entry_prefer)

        val player = players[p0]

        nameView.text = player.name
        CoroutineScope(Dispatchers.IO).launch {
            val playerImageDrawable = PlayerRepository.findImage(player.playerImageUrl)
                ?: ContextCompat.getDrawable(context, R.drawable.no_player)
            val seasonImageDrawable = PlayerRepository.findImage(SeasonRepository.getImageSrc(player.sid))

            CoroutineScope(Dispatchers.Main).launch {
                imageView.setImageDrawable(playerImageDrawable)
                seasonView.setImageDrawable(seasonImageDrawable)
            }
        }
        payView.text = player.pay.toString()
        preferView.text = player.prefer.map { "${it.key} ${it.value}" }.joinToString(" ")

        view.setOnClickListener {
            context.startActivity(Intent(context, PlayerInfoActivity::class.java).apply {
                putExtra("squadId", squadId)
                putExtra("playerPosition", position)
                putExtra("playerId", player.id)
            })
        }

        return view
    }
}