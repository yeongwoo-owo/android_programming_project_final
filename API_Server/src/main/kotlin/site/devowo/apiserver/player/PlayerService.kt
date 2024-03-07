package site.devowo.apiserver.player

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlayerService(private val playerRepository: PlayerRepository) {

    fun getPlayer(playerId: Long): PlayerResponse {
        val player = playerRepository.findByIdOrNull(playerId) ?: throw RuntimeException("No Player Exist")
        val positionMap = mutableMapOf<String, Int>()
        player.position.forEach { positionMap[it.position.name] = it.value }
        val preferMap = mutableMapOf<String, Int>()
        player.prefer.forEach { preferMap[it.position.name] = positionMap[it.position.name]!! }
        val detailMap = mutableMapOf<String, Int>()
        player.stat.forEach { detailMap[it.name] = it.value }

        return PlayerResponse(
            id = player.id,
            name = player.name,
            spid = player.spid,
            sid = player.spid / 1_000_000,
            pid = player.spid % 1_000_000,
            pay = player.pay,
            prefer = preferMap,
            hidden = player.hiddenSkill.map { it.name },
            stat = PlayerStatResponse(
                position = positionMap,
                detail = detailMap,
            ),
            info = player.info
        )
    }

    fun getPlayers(name: String): PlayerListResponse {
        val players = playerRepository.findAllByName("%$name%")
        return PlayerListResponse(
            players = players.map { player ->

                val positionMap = mutableMapOf<String, Int>()
                player.position.forEach { positionMap[it.position.name] = it.value }
                val preferMap = mutableMapOf<String, Int>()
                player.prefer.forEach { preferMap[it.position.name] = positionMap[it.position.name]!! }

                PlayerEntryResponse(
                    id = player.id,
                    name = player.name,
                    spid = player.spid,
                    pay = player.pay,
                    prefer = preferMap
                )
            }
        )
    }
}
