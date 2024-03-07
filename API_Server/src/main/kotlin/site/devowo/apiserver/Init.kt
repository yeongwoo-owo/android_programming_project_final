package site.devowo.apiserver

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import site.devowo.apiserver.player.HiddenSkill
import site.devowo.apiserver.player.Player
import site.devowo.apiserver.player.PlayerInfo
import site.devowo.apiserver.player.PlayerPosition
import site.devowo.apiserver.player.PlayerStat
import site.devowo.apiserver.player.Position
import site.devowo.apiserver.player.PreferPosition

@Profile("!prod")
@Component
class Init(private val initClass: InitClass) {

    private val restTemplate = RestTemplate()

    @PostConstruct
    fun setup() {
        val spids = restTemplate.getForObject(
            "https://static.api.nexon.co.kr/fifaonline4/latest/spid.json",
            Array<Spid>::class.java
        )?.toList()

        var i = 0.0

        spids?.forEach {
            initClass.setup(it.id)
            val percent = i++ / spids.size * 100
            println("Progress: ${percent}%")
        }
    }

    @Component
    @Transactional
    class InitClass(private val em: EntityManager) {

        private val restTemplate = RestTemplate()

        fun setup(spid: Int) {
            if (em.createQuery("select p from Player p where p.spid = :spid", Player::class.java)
                .setParameter("spid", spid)
                .resultList.size > 0) return

            val response = restTemplate.getForObject(
                "https://lexpfrrcwc.execute-api.ap-northeast-2.amazonaws.com/dev/players/${spid}",
                PlayerResponse::class.java
            ) ?: return

            val playerInfo = PlayerInfo(
                response.info.birth,
                response.info.height,
                response.info.weight,
                response.info.physical,
                response.info.skill,
                response.info.foot,
                response.info.season,
                response.info.team,
                response.info.nation
            )
            val player = Player(
                response.name,
                response.spid,
                response.pay,
                playerInfo
            )
            em.persist(player)
            response.stat.prefer.forEach {
                em.persist(PreferPosition(player, Position.valueOf(it.key.uppercase())))
            }
            response.stat.position.forEach {
                em.persist(PlayerPosition(player, Position.valueOf(it.key.uppercase()), it.value))
            }
            response.stat.detail.forEach {
                em.persist(PlayerStat(player, it.key, it.value))
            }
            response.info.hidden.forEach {
                em.persist(HiddenSkill(player, it))
            }
        }
    }

    data class Spid(val id: Int, val name: String)

    data class PlayerResponse(
        val name: String,
        val pay: Int,
        val spid: Int,
        val stat: PlayerStat,
        val info: PlayerInfo
    ) {
        data class PlayerStat(
            val prefer: Map<String, Int>,
            val position: Map<String, Int>,
            val detail: Map<String, Int>
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
            val nation: String,
            val hidden: List<String>
        )
    }
}

