import re
import hashlib
from flask import Flask, json, jsonify, request, make_response
import requests
import easyocr
from flask_session import Session
from algorithm import Algorithm                 # 개인정보 탐지 알고리즘 관련


####################################################################
# 임시 DB
class GtnServer():
    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}  # 허용 파일 확장자
    user_id = "BillyMin"                # BillyMin
    pw = "test1234!"                    # test123!
    salt = None                         # secret
    # hash = hashlib.sha256(str(pw + salt).encode('utf-8')).hexdigest()
    access_token = "ToKeN"              # PyJWT 사용          
    decry_key = None                    # asdf(temp)

    # 파일 확장자 검증 함수
    def allowed_file(filename):
        return '.' in filename and filename.rsplit('.', 1)[1].lower() in GtnServer.ALLOWED_EXTENSIONS
####################################################################

# easyOCR 관련
class EasyOcr():
    reader = easyocr.Reader(['ko', 'en'], gpu=False)
    coordinate = {}                                         # 좌표값 저장용 사전
    jumin_counter = 0                                       # 개인정보(주민번호) 갯수 카운팅 변수
    license_counter = 0                                     # 개인정보(면허번호) 갯수 카운팅 변수
    tag = [0,0,0]                                           # 개인정보 유형 확인용 리스트

    # 인식 텍스트 좌표값 추출 함수
    def get_coordinate(result):
        # bounding box 좌표, 텍스트, 검증(1에 가까울수록 정확도 높음)
        # KITTY, VOC 형식
        for (bbox, text, prob) in result:
            # top left, top right, bottom right, bottom left
            (tl, tr, br, bl) = bbox
            tl = (int(tl[0]), int(tl[1]))
            tr = (int(tr[0]), int(tr[1]))
            br = (int(br[0]), int(br[1]))
            bl = (int(bl[0]), int(bl[1]))

            
            if Algorithm.is_idcard(text):
                EasyOcr.coordinate.update({"tag": "idcard"})

            if Algorithm.is_license(text):
                EasyOcr.coordinate.update({"tag": "license"})

            if Algorithm.is_registration(text):
                EasyOcr.coordinate.update({"tag": "registration"})

            if Algorithm.jumin_check(text):
                EasyOcr.jumin_counter += 1
                EasyOcr.coordinate.update({"jumin {}".format(EasyOcr.jumin_counter): [{"x": tl[0], "y":tl[1]}, {
                                          "x": tr[0], "y":tr[1]}, {"x": br[0], "y":br[1]}, {"x": bl[0], "y":bl[1]}]})

            if Algorithm.licensenum_check(text):
                EasyOcr.license_counter += 1
                EasyOcr.coordinate.update({"license {}".format(EasyOcr.license_counter): [{"x": tl[0], "y":tl[1]}, {
                                          "x": tr[0], "y":tr[1]}, {"x": br[0], "y":br[1]}, {"x": bl[0], "y":bl[1]}]})

        return EasyOcr.coordinate

####################################################################
# app 구성 영역
app = Flask(__name__)

# test API
@app.route('/', methods=['GET'])
def test():
    if request.method == 'GET':
        response = {
            "test": request.url,
            "result": 'OK'
        }
        res = make_response(jsonify(response), 200)
        return res
    else:
        return 'None'

# Login API
@app.route('/login', methods=['POST'])
def login_auth():     # 임시
    auth_data = request.get_json()
    # print(auth_data)
    if auth_data.get('id') == GtnServer.user_id and auth_data.get('pw') == GtnServer.pw:
        return jsonify({
            "access_token": GtnServer.access_token
        })
    else:
        return jsonify({
            "msg": "계정 정보가 일치하지 않습니다."
        })

# Decryption API
@app.route('/decryption', methods=['POST', 'GET'])
def get_key():
    pass
    auth_token = request.get_json()
    # 인증 성공 - 토큰 일치
    if auth_token.get('access_token') == GtnServer.token:
        return jsonify({
            'decry_key': GtnServer.decry_key
        })
    # 인증 실패 - 토큰 불일치
    else:
        return {
            "msg": "권한이 없는 요청입니다."
        }, 401

# OCR API
@app.route('/ocr', methods=['POST'])
def ocr():
    if request.method == 'POST':        
        # 파일이 첨부되어 있는가 확인
        if 'file' not in request.files:
            return jsonify({
                'msg': '파일이 없습니다.'
            })
        
        file = request.files['file']
        
        if file and GtnServer.allowed_file(file.filename):
            # 변수 리셋
            EasyOcr.coordinate = {}
            EasyOcr.jumin_counter = 0
            EasyOcr.license_counter = 0
            EasyOcr.tag = [0, 0]

            parsed = EasyOcr.reader.readtext(file.read())
            contents = EasyOcr.get_coordinate(parsed)
            # 개인정보 탐지 내용이 없을 경우
            if contents == {}:
                return jsonify({
                    'msg': 'No Contents'
                })
            # 개인정보 탐지 내용이 있을 경우
            else:
                return jsonify(contents)
                # return json.dumps(contents, ensure_ascii=False, sort_keys=True)
                # body = json.dumps(contents, ensure_ascii=False, sort_keys=True)
                # return {
                    # 'data': body
                # }
                # return body
        # 파일 형식이 허용되지 않을 경우
        else:
            return jsonify({
                'msg': '허용되지 않는 파일 형식입니다.'
            }), 403
    # GET 방식 테스트용 임시
    else:
        return jsonify({
            'msg': '허용되지 않은 접근입니다.'
        }), 403
####################################################################

# 서버 구동 영역
if __name__ == '__main__':
    app.config['SECRET_KEY'] = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    sess = Session()
    app.run(host='0.0.0.0', port=9000)
