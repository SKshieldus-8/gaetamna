import os
import re
from flask import Flask, json, jsonify, request, flash, redirect, make_response
import requests
import easyocr
from flask_session import Session
from algorithm import Algorithm                 # 개인정보 탐지 알고리즘 관련


# 임시 DB
####################################################################
class GtnServer():
    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}  # 허용 파일 확장자
    id = None
    pw = None
    token = None
    decry_key = None

    # 파일 확장자 검증 함수
    def allowed_file(filename):
        return '.' in filename and filename.rsplit('.', 1)[1].lower() in GtnServer.ALLOWED_EXTENSIONS
####################################################################

# easyOCR 관련
class EasyOcr():
    reader = easyocr.Reader(['ko', 'en'], gpu=False)
    coordinate = {}                                         # 좌표값 저장용 사전
    counter = 0                                             # 개인정보 갯수 카운팅 변수

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

            if Algorithm.ssn_check(text):
                EasyOcr.counter += 1
                EasyOcr.coordinate.update({"vertices {}".format(EasyOcr.counter): [{"x": tl[0], "y":tl[1]}, {
                                          "x": tr[0], "y":tr[1]}, {"x": br[0], "y":br[1]}, {"x": bl[0], "y":bl[1]}]})
        return EasyOcr.coordinate

# app 구성 영역
####################################################################
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
def login_auth(id, pw):     # 임시
    # auth_data = request._contents
    # if auth_data.id == GtnServer.id and auth_data.pw == GtnServer.pw:
    if id == GtnServer.id and pw == GtnServer.pw:           # 임시 - DB 호출
        return {
            "token": GtnServer.token
        }
    else:
        return {
            "msg": "계정 정보가 일치하지 않습니다."
        }

# Decryption API
@app.route('/decryption', methods=['POST', 'GET'])
def get_key():
    pass
    # 인증 성공 - 토큰 일치
    # if token == GtnServer.token:
        # return {
            # 'key': GtnServer.decry_key
        # }
    # 인증 실패 - 토큰 불일치
    # else:
        # return {
            # "msg": "권한이 없는 요청입니다."
        # }

# OCR API
@app.route('/ocr', methods=['GET', 'POST'])
def ocr():
    if request.method == 'POST':        
        # 파일이 첨부되어 있는가 확인
        if 'file' not in request.files:
            return {
                'msg': '파일이 없습니다.'
            }
            # flash('Not allowed file')
            # return redirect(request.url)
        
        file = request.files['file']
        
        if file and GtnServer.allowed_file(file.filename):
            EasyOcr.counter = 0                         # 개인정보 갯수 카운터 초기화
            parsed = EasyOcr.reader.readtext(file.read())
            contents = EasyOcr.get_coordinate(parsed)
            # 개인정보 탐지 내용이 없을 경우
            if contents == {}:
                return {
                    'msg': 'No Contents'
                }
            # 개인정보 탐지 내용이 있을 경우
            else:
                body = json.dumps(contents, ensure_ascii=False, sort_keys=True)
                return body
                # return {
                    # 'data': body
                # }
        # 파일 형식이 허용되지 않을 경우
        else:
            return {
                'msg': '허용되지 않는 파일 형식입니다.'
            }
    # GET 방식 테스트용 임시
    else:
        return 'Nothing'
    '''
    <!doctype html>
    <title>Upload new File</title>
    <h1>Upload new File</h1>
    <form method=post enctype=multipart/form-data>
      <input type=file name=file>
      <input type=submit value=Upload>
    </form>
    '''
####################################################################

# 서버 구동 영역
if __name__ == '__main__':
    app.config['SECRET_KEY'] = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    sess = Session()
    app.run(host='0.0.0.0', port=9000)
