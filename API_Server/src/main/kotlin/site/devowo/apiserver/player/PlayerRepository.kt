package site.devowo.apiserver.player

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlayerRepository: JpaRepository<Player, Long> {

    @Query("select p from Player p where p.name like :name order by p.pay desc")
    fun findAllByName(name: String): List<Player>
}
