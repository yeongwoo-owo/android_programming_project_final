package edu.skku.cs.pp.model

data class Player(
    val id: Long,
    val name: String,
    val pay: Int,
    val spid: Int,
    val prefer: Map<String, Int>,
    val hidden: List<String> = listOf(),
    val stat: PlayerStat?,
    val info: PlayerInfo?
) {
    val ovr get() = prefer.values.max()
    val sid get() = spid / 1_000_000
    val playerImageUrl get() = "https://fo4.dn.nexoncdn.co.kr/live/externalAssets/common/playersAction/p${spid}.png"

    fun getStatList() = stat?.detail?.toList()?.sortedBy { it.second }?.reversed() ?: emptyList()

    data class PlayerStat(
        val position: Map<String, Int> = mapOf(),
        val detail: Map<String, Int> = mapOf()
    )

    data class PlayerInfo(
        val birth: String,
        val height: String,
        val weight: String,
        val physical: String,
        val skill: String,
        val foot: String,
        val season: String,
        val team: String?,
        val nation: String
    )
}