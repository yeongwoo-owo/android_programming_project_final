package site.devowo.apiserver.player

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(indexes = [
    Index(name = "idx__name", columnList = "name"),
    Index(name = "idx__spid", columnList = "spid")])
class Player(

    val name: String,
    val spid: Int,
    val pay: Int,

    @Embedded
    val info: PlayerInfo,

    @OneToMany(mappedBy = "player")
    val prefer: List<PreferPosition> = listOf(),

    @OneToMany(mappedBy = "player")
    val position: List<PlayerPosition> = listOf(),

    @OneToMany(mappedBy = "player")
    val stat: List<PlayerStat> = listOf(),

    @OneToMany(mappedBy = "player")
    val hiddenSkill: List<HiddenSkill> = listOf(),

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    val id: Long = 0L
)

@Entity
class PreferPosition(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    val player: Player,

    @Enumerated(EnumType.STRING)
    val position: Position,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prefer_position_id")
    val id: Long = 0L
)

@Entity
class PlayerPosition(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    val player: Player,

    @Enumerated(EnumType.STRING)
    val position: Position,

    val value: Int,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_position_id")
    val id: Long = 0L
)

@Entity
class PlayerStat(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    val player: Player,

    val name: String,

    val value: Int,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_stat_id")
    val id: Long = 0L
)

@Embeddable
class PlayerInfo(
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

@Entity
class HiddenSkill(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    val player: Player,

    val name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hidden_skill_id")
    val id: Long = 0L
)

enum class Position {
    GK,
    SW, RWB, RB, RCB, CB, LCB, LB, LWB,
    RDM, CDM, LDM, RM, RCM, CM, LCM, LM, RAM, CAM, LAM,
    RF, CF, LF, RW, RS, ST, LS, LW,
    SUB
}