package site.devowo.apiserver.player

data class PlayerResponse(
    val id: Long,
    val name: String,
    val spid: Int,
    val sid: Int,
    val pid: Int,
    val pay: Int,
    val prefer: Map<String, Int>,
    val hidden: List<String>,
    val stat: PlayerStatResponse,
    val info: PlayerInfo
)
data class PlayerStatResponse(
    val position: Map<String, Int>,
    val detail: Map<String, Int>
)
data class PlayerListResponse(
    val players: List<PlayerEntryResponse>
)
data class PlayerEntryResponse(
    val id: Long,
    val name: String,
    val spid: Int,
    val pay: Int,
    val prefer: Map<String, Int>
)