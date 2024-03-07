package edu.skku.cs.pp.player

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.skku.cs.pp.R
import edu.skku.cs.pp.repository.PlayerRepository
import edu.skku.cs.pp.repository.SquadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_search)
        title = "선수 검색"

        val squadId = intent.getLongExtra("squadId", -1)
        val playerPosition = intent.getStringExtra("playerPosition")!!

        val editTextView = findViewById<EditText>(R.id.player_search_edit_text)
        val button = findViewById<Button>(R.id.player_search_button)
        val listView = findViewById<ListView>(R.id.player_search_list)

        button.setOnClickListener {
            val name = editTextView.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val players = PlayerRepository.findBy(name)
                CoroutineScope(Dispatchers.Main).launch {
                    if (players.isEmpty()) {
                        Toast.makeText(
                            this@PlayerSearchActivity,
                            "검색 결과가 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        listView.adapter = PlayerSearchListAdapter(this@PlayerSearchActivity, players, squadId, playerPosition)
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