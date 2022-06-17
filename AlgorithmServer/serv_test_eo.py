from flask import Flask, json, jsonify, request, make_response
import hashlib
import requests
import easyocr
import os
import secrets  # 랜덤난수 생성(key2)
from flask_session import Session
from processing import EasyOcr, PreProcessing                   # OCR
from cryptography.hazmat.primitives.ciphers.aead import AESGCM  # Galois Counter Mode의 AES-GCM 대칭키 암호화 모듈 (key3)
from cryptography.fernet import Fernet                          # 대칭키 암호화 모듈 (key1)

####################################################################
# 임시 DB
class GtnServer():
    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}  # 허용 파일 확장자
    user_id = "msg7883"                # BillyMin
    pw = "test1234!"                    # test123!
    salt = "skshieldus"                         # secret
    access_token = "token"              # PyJWT 사용

    key2 = secrets.token_hex(8)
    test_message = b"Test message for encryption"

    key1 = Fernet.generate_key()
    ciphertext = Fernet(key1).encrypt(test_message)        # 암호화문
    plaintext = Fernet(key1).decrypt(ciphertext)                             # 평문 - 복호화

    hash = hashlib.sha256(str(key2 + salt).encode('utf-8')).hexdigest()

    data = b"secret key"                # 암호화 내용
    key3 = AESGCM.generate_key(bit_length=128)

    decry_key = "Key"                    # asdf(temp)

    # 파일 확장자 검증 함수
    def allowed_file(filename):
        return '.' in filename and filename.rsplit('.', 1)[1].lower() in GtnServer.ALLOWED_EXTENSIONS
####################################################################


####################################################################
# app 구성 영역
app = Flask(__name__)

# test API
@app.route('/', methods=['GET'])
def test():
    if request.method == 'GET':
        response = {
            "test": request.url,
            "result": "OK"
        }
        res = make_response(jsonify(response), 200)
        return res
    else:
        return "None"

# Login API
@app.route('/login', methods=['POST'])
def login_auth():     # 임시
    auth_data = request.form
    # print(auth_data)
    if auth_data['id'] == GtnServer.user_id and auth_data['pw'] == GtnServer.pw:
        onekey = secrets.token_hex(8)
        iv = secrets.token_hex(8)
        return jsonify({
            "result": 1,
            "access_token": GtnServer.access_token,
            "key": onekey,
            "iv": iv
        })
    else:
        return jsonify({
            "result": 0,
            "msg": "계정 정보가 일치하지 않습니다."
        })

# Decryption API
@app.route('/decryption', methods=['POST', 'GET'])
def get_key():
    pass
    auth_token = request.form
    # 인증 성공 - 토큰 일치
    if auth_token['access_token'] == GtnServer.access_token:
        return jsonify({
            "result": 1,
            "decry_key": GtnServer.decry_key
        })
    # 인증 실패 - 토큰 불일치
    else:
        return {
            "result": 0,
            "msg": "권한이 없는 요청입니다."
        }, 401

# OCR API
@app.route('/ocr', methods=['POST'])
def ocr():
    if request.method == 'POST': 

        print(GtnServer.key1)                 # Fernet 대칭키
        print(type(GtnServer.key2))
        print(GtnServer.ciphertext)
        print(type(GtnServer.ciphertext))
        print(GtnServer.plaintext)
        # print(GtnServer.key2)
        # print(GtnServer.key3)
        # print(GtnServer.hash)

        # 파일이 첨부되어 있는가 확인
        if 'file' not in request.files:
            return jsonify({
                "result": 0,
                "msg": "파일이 없습니다."
            })
        
        file = request.files['file']
        
        if file and GtnServer.allowed_file(file.filename):
            # 검증에 필요한 자료구조 및 변수
            tag = str()
            # coordinate = dict()
            coordinate = list()
            verif_idcard = list(0 for i in range(0, 5))
            verif_license = list(0 for i in range(0, 5))
            verif_regist = list(0 for i in range(0, 9))
            jumin_cnt = 0
            license_cnt = 0

            parsed = EasyOcr.reader.readtext(file.read())
            contents = list(EasyOcr.get_coordinate(parsed, tag, coordinate, verif_idcard, verif_license, verif_regist, jumin_cnt, license_cnt))
            
            # print(type(contents))
            # print(contents)

            # print(contents)
            # print(type(contents))
            # print(contents[0])
            # 개인정보 탐지 내용이 없을 경우
            if contents[1] == []:
                return jsonify({
                    "result": 0,
                    "msg": "No Contents",
                    "tag": "",
                    "count": 0,
                    "data": []
                })
            # 개인정보 탐지 내용이 있을 경우
            else:
                # return jsonify(contents)
                # return json.dumps(contents, ensure_ascii=False, sort_keys=True)
                # body = json.dumps(contents, ensure_ascii=False, sort_keys=True)
                # return body
                return jsonify({
                    "result": 1,
                    "msg": "",
                    "tag": contents[0],
                    "count": contents[2],
                    "data": contents[1]
                })
        # 파일 형식이 허용되지 않을 경우
        else:
            return jsonify({
                "result": 0,
                "msg": "허용되지 않는 파일 형식입니다."
            }), 403
    # POST 의외의 방식 방지
    else:
        return jsonify({
            "result": 0,
            "msg": "허용되지 않은 접근입니다."
        }), 403
####################################################################

# 서버 구동 영역
if __name__ == '__main__':
    app.config['SECRET_KEY'] = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    sess = Session()
    app.run(host='0.0.0.0', port=9000)
