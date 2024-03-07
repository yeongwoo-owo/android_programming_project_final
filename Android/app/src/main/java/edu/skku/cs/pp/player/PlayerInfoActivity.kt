package edu.skku.cs.pp.player

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.Position
import edu.skku.cs.pp.repository.PlayerRepository
import edu.skku.cs.pp.repository.SeasonRepository
import edu.skku.cs.pp.repository.SquadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL

class PlayerInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_info)

        val squadId = intent.getLongExtra("squadId", -1)
        val playerPosition = intent.getStringExtra("playerPosition")
        val playerId = intent.getLongExtra("playerId", -1)
        val isUpdatable = intent.getBooleanExtra("isUpdatable", false)

        CoroutineScope(Dispatchers.IO).launch {
            val player = PlayerRepository.findBy(playerId)
            val seasonImageDrawable = PlayerRepository.findImage(SeasonRepository.getImageSrc(player.sid))
            val playerImageDrawable = PlayerRepository.findImage(player.playerImageUrl)
                ?: ContextCompat.getDrawable(this@PlayerInfoActivity, R.drawable.no_player)
            CoroutineScope(Dispatchers.Main).launch {
                this@PlayerInfoActivity.title = player.name

                val playerImage = findViewById<ImageView>(R.id.player_info_image)
                playerImage.setImageDrawable(playerImageDrawable)

                val seasonView = findViewById<ImageView>(R.id.player_info_season)
                seasonView.setImageDrawable(seasonImageDrawable)

                val nameView = findViewById<TextView>(R.id.player_info_name)
                nameView.text = player.name

                val preferView = findViewById<TextView>(R.id.player_info_prefer)
                preferView.text = player.prefer.map { "${it.key} ${it.value}" }.joinToString(" ")

                val payView = findViewById<TextView>(R.id.player_info_pay)
                payView.text = player.pay.toString()

                val statView = findViewById<ListView>(R.id.player_stat_list)
                statView.adapter = PlayerStatAdapter(this@PlayerInfoActivity, player.getStatList())

                val button = findViewById<Button>(R.id.change_player)
                button.text = if (isUpdatable) "변경" else "등록"
                button.setOnClickListener {
                    if (isUpdatable) {
                        startActivity(Intent(this@PlayerInfoActivity, PlayerSearchActivity::class.java).apply {
                            putExtra("squadId", squadId)
                            putExtra("playerPosition", playerPosition)
                        })
                    } else {
                        val squad = SquadRepository.getSquad(this@PlayerInfoActivity, squadId)
                        val position = Position.valueOf(playerPosition!!)
                        squad.setPlayer(player, position)
                        SquadRepository.updateSquad(this@PlayerInfoActivity, squad)
                        SquadRepository.isUpdated = true
                        finish()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (SquadRepository.isUpdated) {
            finish()
        }
    }
}