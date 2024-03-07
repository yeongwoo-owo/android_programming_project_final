class Player:
    def __init__(self, spid, name, pay, prefer, position, birth, height, weight, physical, skill, foot, season, team, nation, hidden, detail):
        self.name = name
        self.spid = spid
        self.pay = pay
        self.stat = self.PlayerStat(prefer, position, detail)
        self.info = self.PlayerInfo(birth, height, weight, physical, skill, foot, season, team, nation, hidden)

    def to_dict(self):
        return {
            "name": self.name,
            "spid": self.spid,
            "pay": self.pay,
            "stat": {
                "prefer": self.stat.prefer,
                "position": self.stat.position,
                "detail": self.stat.detail
            },
            "info": {
                "birth": self.info.birth,
                "height": self.info.height,
                "weight": self.info.weight,
                "physical": self.info.physical,
                "skill": self.info.skill,
                "foot": self.info.foot,
                "season": self.info.season,
                "team": self.info.team,
                "nation": self.info.nation,
                "hidden": self.info.hidden,
            }
        }

    class PlayerStat:
        def __init__(self, prefer, position, detail):
            self.prefer = prefer
            self.position = position
            self.detail = detail

    class PlayerInfo:
        def __init__(self, birth, height, weight, physical, skill, foot, season, team, nation, hidden):
            self.birth = birth
            self.height = height
            self.weight = weight
            self.physical = physical
            self.skill = skill
            self.foot = foot
            self.season = season
            self.team = team
            self.nation = nation
            self.hidden = hidden