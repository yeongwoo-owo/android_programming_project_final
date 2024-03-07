package site.devowo.apiserver.player

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController(private val playerService: PlayerService) {

    @GetMapping("/players/{playerId}")
    fun getPlayer(@PathVariable playerId: Long): PlayerResponse {
        return playerService.getPlayer(playerId)
    }

    @GetMapping("/players")
    fun getPlayers(@RequestParam name: String): PlayerListResponse {
        return playerService.getPlayers(name)
    }
}
