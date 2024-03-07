package edu.skku.cs.pp.model

import edu.skku.cs.pp.R

data class Squad(
    val name: String,
    var formation: Formation = formations[0],
    val players: List<PlayerPosition> = Array(11) {idx -> PlayerPosition(position = formation.positions[idx])}.toList(),
    var id: Long = 0L
) {

    val fwStat: Int get() {
        return formation.positions.zip(players)
            .filter { it.first.type == PositionType.FW }
            .filterNot { it.second.player == null }
            .map { it.second.player!!.ovr }
            .average().toInt()
    }

    val mfStat: Int get() {
        return formation.positions.zip(players)
            .filter { it.first.type == PositionType.MF }
            .filterNot { it.second.player == null }
            .map { it.second.player!!.ovr }
            .average().toInt()
    }

    val dfStat: Int get() {
        return formation.positions.zip(players)
            .filter { it.first.type == PositionType.DF }
            .filterNot { it.second.player == null }
            .map { it.second.player!!.ovr }
            .average().toInt()
    }

    fun getPlayer(position: Position): PlayerPosition {
        val idx = formation.positions.indexOf(position)
        return players[idx]
    }

    fun getPlayerView(position: Position): Int {
        val playerViews = listOf(
            R.id.player_holder_1,
            R.id.player_holder_2,
            R.id.player_holder_3,
            R.id.player_holder_4,
            R.id.player_holder_5,
            R.id.player_holder_6,
            R.id.player_holder_7,
            R.id.player_holder_8,
            R.id.player_holder_9,
            R.id.player_holder_10,
            R.id.player_holder_11,
        )
        val idx = formation.positions.indexOf(position)
        return playerViews[idx]
    }

    fun resetCoordinates() {
        players.map { it.coordinate = null }
    }

    fun setPlayer(player: Player, position: Position) {
        val idx = formation.positions.indexOf(position)
        players[idx].player = player
    }
}

data class PlayerPosition(var player: Player? = null, val position: Position? = null, var coordinate: Coordinate? = null)