from flask import Flask, jsonify

import player_crawler

app = Flask(__name__)


@app.route('/players/<spid>')
def find_by_spid(spid):
    return jsonify(player_crawler.find(spid).to_dict())


if __name__ == '__main__':
    app.run(port=5000, debug=True)
