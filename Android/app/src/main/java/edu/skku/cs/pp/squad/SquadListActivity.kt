package edu.skku.cs.pp.squad

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.Squad
import edu.skku.cs.pp.repository.SeasonRepository
import edu.skku.cs.pp.repository.SquadRepository

class SquadListActivity : AppCompatActivity() {

    init {
        SeasonRepository.init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad_list)
    }

    override fun onResume() {
        super.onResume()

        findViewById<ListView>(R.id.squad_list).also {
            val squadList = SquadRepository.getSquadList(this)
            val adapter = SquadListAdapter(this, squadList)
            it.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formation_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.formation_list_action_add -> {
                val editText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("스쿼드 추가")
                    .setMessage("스쿼드 이름을 입력하세요.")
                    .setView(editText)
                    .setPositiveButton("확인") { dialog: DialogInterface, id: Int ->
                        val squadName = editText.text.toString()
                        val squad = SquadRepository.addSquad(this, Squad(squadName))
                        startActivity(Intent(this, SquadActivity::class.java).apply {
                            putExtra("squadId", squad.id)
                        })
                    }
                    .setCancelable(true)
                    .create()
                    .show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}