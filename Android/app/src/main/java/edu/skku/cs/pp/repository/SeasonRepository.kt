package edu.skku.cs.pp.repository

import edu.skku.cs.pp.NetworkManager
import edu.skku.cs.pp.model.Season
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SeasonRepository {

    private var seasons: List<Season> = listOf()

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            seasons = NetworkManager.fetch(
                "https://static.api.nexon.co.kr/fifaonline4/latest/seasonid.json",
                Array<Season>::class.java
            ).toList()
        }
    }

    fun getImageSrc(sid: Int): String {
        return seasons.find { it.seasonId == sid }!!.seasonImg
    }
}