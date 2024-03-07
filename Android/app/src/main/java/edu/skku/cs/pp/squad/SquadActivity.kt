package edu.skku.cs.pp.squad

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.Coordinate
import edu.skku.cs.pp.model.Squad
import edu.skku.cs.pp.model.formations
import edu.skku.cs.pp.player.PlayerInfoActivity
import edu.skku.cs.pp.player.PlayerSearchActivity
import edu.skku.cs.pp.repository.PlayerRepository
import edu.skku.cs.pp.repository.SquadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SquadActivity : AppCompatActivity() {

    private var onInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)
        val squadId = intent.getLongExtra("squadId", -1)
        val squad = SquadRepository.getSquad(this, squadId)
        title = squad.name
        setSpinner(squad)
        val changeFormationButton = findViewById<Button>(R.id.change_formation_button)
        changeFormationButton.setOnClickListener {
            squad.resetCoordinates()
            SquadRepository.updateSquad(this, squad)
            this.recreate()
        }

        squad.formation.positions.forEach { position ->
            val positionViewHolder = findViewById<ConstraintLayout>(position.view)
            val positionView = positionViewHolder.findViewById<TextView>(R.id.position_text)
            positionView.text = position.name
            positionView.visibility = View.INVISIBLE
            val playerViewHolder = findViewById<LinearLayout>(squad.getPlayerView(position))

            val playerPosition = squad.getPlayer(position)
            if (playerPosition.coordinate == null) {
                positionViewHolder.viewTreeObserver.addOnGlobalLayoutListener {
                    playerPosition.coordinate = Coordinate(
                        x = (positionViewHolder.x + (positionViewHolder.width - playerViewHolder.width) / 2),
                        y = (positionViewHolder.y + (positionViewHolder.height - playerViewHolder.height) / 2)
                    )
                    playerViewHolder.x = playerPosition.coordinate!!.x
                    playerViewHolder.y = playerPosition.coordinate!!.y
                    SquadRepository.updateSquad(this, squad)
                }
            } else {
                playerViewHolder.viewTreeObserver.addOnGlobalLayoutListener {
                    playerViewHolder.x = playerPosition.coordinate!!.x
                    playerViewHolder.y = playerPosition.coordinate!!.y
                }
            }

            val player = playerPosition.player
            val backgroundView =
                playerViewHolder.findViewById<ImageView>(R.id.player_image_background)
            backgroundView.setColorFilter(getColor(position.type.color))
            val playerImageView = playerViewHolder.findViewById<ImageView>(R.id.player_image)
            CoroutineScope(Dispatchers.IO).launch {
                val playerImageDrawable =
                    player?.playerImageUrl?.let { PlayerRepository.findImage(player.playerImageUrl) }
                        ?: ContextCompat.getDrawable(this@SquadActivity, R.drawable.plus)

                CoroutineScope(Dispatchers.Main).launch {
                    playerImageView.setImageDrawable(playerImageDrawable)
                }
            }

            val playerOvrView = playerViewHolder.findViewById<TextView>(R.id.player_ovr_text)
            val playerPositionView = playerViewHolder.findViewById<TextView>(R.id.player_position_text)
            val playerNameView = playerViewHolder.findViewById<TextView>(R.id.player_name_text)
            val playerPayView = playerViewHolder.findViewById<TextView>(R.id.player_pay_text)
            playerOvrView.text = player?.ovr?.toString() ?: ""
            playerPositionView.text = position.name
            playerNameView.text = player?.name ?: ""
            playerPayView.text = player?.pay?.toString() ?: ""

            playerImageView.setOnClickListener {
                if (player == null) {
                    startActivity(Intent(this, PlayerSearchActivity::class.java).apply {
                        putExtra("squadId", squadId)
                        putExtra("playerPosition", position.name)
                    })
                } else {
                    startActivity(Intent(this, PlayerInfoActivity::class.java).apply {
                        putExtra("squadId", squadId)
                        putExtra("playerPosition", position.name)
                        putExtra("playerId", player.id)
                        putExtra("isUpdatable", true)
                    })
                }
            }

            if (player == null) {
                playerImageView.setImageResource(R.drawable.plus)
            } else {
                playerImageView.setImageResource(R.drawable.no_player)
            }
        }

        val squadRemoveButton = findViewById<Button>(R.id.squad_remove_button)
        squadRemoveButton.setOnClickListener {
            SquadRepository.removeSquad(this, squad)
            finish()
        }
    }

    private fun setSpinner(squad: Squad) {
        val formationNames = formations.map { it.name }
        val adapter = ArrayAdapter(
            this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, formationNames
        )
        val spinner = findViewById<Spinner>(R.id.formation_spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (onInit) {
                    onInit = false
                    spinner.setSelection(formations.indexOf(squad.formation))
                } else {
                    squad.formation = formations[p2]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinner.setSelection(formations.indexOf(squad.formation))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (SquadRepository.isUpdated) {
            SquadRepository.isUpdated = false
            this.recreate()
        }
    }
}