import requests
from bs4 import BeautifulSoup

from player import Player


def find(spid):
    base_url = 'https://fifaonline4.nexon.com/DataCenter/PlayerInfo?spid='

    response = requests.get(base_url + spid)

    soup = BeautifulSoup(response.text, 'html.parser')
    player_header = soup.find('div', 'content_header')
    player_name = player_header.find('div', 'name').text.strip()

    prefer_data = player_header.find_all('span', 'position')
    prefer = {}
    for position in prefer_data:
        name = position.find('span', 'txt').text
        value = int(position.find('span', f'skillData_{spid}').text)
        prefer[name] = value

    position_data = player_header.find('div', 'ovr_set').find_all('div', 'position')
    position = {}
    for ovr in position_data:
        name = ovr['class'][1]
        value = ovr.text
        position[name] = value

    pay = int(player_header.find('div', 'pay_side').text)
    birth = player_header.find('span', 'birth').text.strip()
    height = player_header.find('span', 'height').text.strip()
    weight = player_header.find('span', 'weight').text.strip()
    physical = player_header.find('span', 'physical').text.strip()
    skill = player_header.find('span', 'skill').text.strip()
    foot = player_header.find('span', 'foot').text.strip()
    season = player_header.find('span', 'season').text.strip()

    # Nullable 조심
    team_data = player_header.find('div', 'team').find('span', 'txt')
    team = team_data.text.strip() if team_data else None
    nation = player_header.find('div', 'info_team').find('div', 'nation').find('span', 'txt').text.strip()
    hidden = list(map(lambda x: x.text, player_header.find('div', 'skill_wrap').find_all('span')))

    player_bottom = soup.find('div', 'content_bottom')
    detail_datas = player_bottom.find_all('li', 'ab')
    detail = {}
    for data in detail_datas:
        token = data.text.split('\n')
        name = token[1]
        value = int(token[2])
        detail[name] = value

    player = Player(
        spid=spid,
        name=player_name,
        pay=pay,
        prefer=prefer,
        position=position,
        birth=birth,
        height=height,
        weight=weight,
        physical=physical,
        skill=skill,
        foot=foot,
        season=season,
        team=team,
        nation=nation,
        hidden=hidden,
        detail=detail)
    return player
