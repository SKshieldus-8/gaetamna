from flask import Flask, request
from flask_restx import Api, Resource


app = Flask(__name__)

api = Api(app, title='GTN API Test', doc="/GTN-api")


server = {}
client = {}
count = 1



@api.route('/server')
class serverget(Resource):
    def get(self):
        global count
        global server
        
        idx = count
        count += 1
        client[idx] = request.json.get('data')
        
        return {
            'server_id': idx,
            'data': server[idx]
        }



@api.route('/client')
class clientpost(Resource):
    def post(self):
        global count
        global client
        
        idx = count
        count += 1
        client[idx] = request.json.post('data')
        
        return {
            'client_id': idx,
            'data': client[idx]
        }


if __name__ == "__main__":
    app.run(host='0.0.0.0', port = 5000, debug=True)