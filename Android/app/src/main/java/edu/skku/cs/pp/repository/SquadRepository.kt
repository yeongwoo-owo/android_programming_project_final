package edu.skku.cs.pp.repository

import android.content.Context
import com.google.gson.Gson
import edu.skku.cs.pp.model.Squad
import java.io.DataInputStream
import java.io.DataOutputStream

object SquadRepository {

    private const val prefix = "Squad-"
    private const val postfix = ".json"

    private val gson = Gson()
    var isUpdated = false

    fun getSquadList(context: Context): List<Squad> {
        val fileNames = context.fileList().filter { it.startsWith(prefix) && it.endsWith(postfix) }
        return fileNames.map { getSquad(context, getSquadId(it)) }
    }

    private fun getSquadId(fileName: String): Long {
        return fileName.drop(prefix.length).dropLast(postfix.length).toLong()
    }

    fun getSquad(context: Context, id: Long): Squad {
        val fis = context.openFileInput(prefix + id + postfix)
        fis.use {
            val dis = DataInputStream(fis)
            dis.use {
                return gson.fromJson(dis.readUTF(), Squad::class.java)
            }
        }
    }

    fun addSquad(context: Context, squad: Squad): Squad {
        val preferences = context.getSharedPreferences("squad", Context.MODE_PRIVATE)
        val id = preferences.getLong("squadId", 0)
        squad.id = id
        preferences.edit().putLong("squadId", id + 1).apply()
        val fos = context.openFileOutput(prefix + squad.id + postfix, Context.MODE_PRIVATE)
        fos.use {
            val dos = DataOutputStream(fos)
            dos.use {
                dos.writeUTF(gson.toJson(squad))
                return squad
            }
        }
    }

    fun updateSquad(context: Context, squad: Squad) {
        val fos = context.openFileOutput(prefix + squad.id + postfix, Context.MODE_PRIVATE)
        fos.use {
            val dos = DataOutputStream(fos)
            dos.use {
                dos.writeUTF(gson.toJson(squad))
            }
        }
    }

    fun removeSquad(context: Context, squad: Squad) {
        context.deleteFile(prefix + squad.id + postfix)
    }
}