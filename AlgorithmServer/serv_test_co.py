import os
import re
import uuid
import time
from flask import Flask, flash, request, redirect, url_for, json, jsonify
from werkzeug.utils import secure_filename
import requests
import easyocr
from flask_session import Session
from algorithm import Algorithm                 # 개인정보 탐지 알고리즘 관련


# app 선언 영역
app = Flask(__name__)

ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}  # 허용 파일 확장자
FILE_DIR = 'AlgorithmServer/files'                  # 파일 저장 경로

# 디렉토리가 없는 경우 디렉토리 생성
if not os.path.exists(FILE_DIR):
    os.makedirs(FILE_DIR)

# 파일 확장자 검증 함수
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


# Naver Clova OCR 관련
class ClovaOcr():
    coordinate = {}                 # 좌표값 저장용 사전
    counter = 0                     # 개인정보 갯수 카운팅 변수
    # 네이버 클로바 ocr api url
    api_url = "https://yny98jcxqj.apigw.ntruss.com/custom/v1/16149/43a5dd752714ac0facb6eed256512ee04bd2a43cd3d7bc43ea6bf31fdc530da0/general"
    # 앱 보안 키(네이버 클로바 키 or 아마존 키)
    secret_key = "dHBtZ0xwQmNuRW5XUWhsS1VDdVJRU0RnREtUVU5BY3c="


# Clova OCR를 이용해 서버에서 작동할 함수 관련
class ActOnServer:
    # API 호출에 대한 응답 함수
    def clova_ocr_response(input):
        input_file = input

        request_json = {
            'images': [
                {
                    'format': 'png',
                    'name': 'demo'
                }
            ],
            'requestId': str(uuid.uuid4()),         # 무작위 UUID 값으로 requestID 생성
            'version': 'V2',                        # V2로 bounding polygon 정보 제공 받음
            'timestamp': int(round(time.time() * 1000))
        }

        payload = {'message': json.dumps(request_json).encode('UTF-8')}
        files = [
            ('file', open(input_file, 'rb'))
        ]
        headers = {
            'X-OCR-SECRET': ClovaOcr.secret_key
        }

        response = requests.request("POST", ClovaOcr.api_url, headers=headers, data=payload, files=files)
        res = json.loads(response.text.encode('utf8'))  # 실 내용 저장 변수
        return res

    # Clova OCR 인식 전체 영역에서 개인정보 영역 좌표값만 추출하는 함수
    def check_privacy(res):
        for images in res["images"]:
            for key in images["fields"]:
                if (Algorithm.ssn_check(key["inferText"])):
                    ClovaOcr.counter += 1
                    ClovaOcr.coordinate.update({"{}.".format(ClovaOcr.counter): key["boundingPoly"]})
        return ClovaOcr.coordinate

    # 최종 반환 .json 파일 추출 함수
    # def extract_coordinate_json(coordinate):
        # with open('AlgorithmServer/clovarocr_coordinate.json', 'w', encoding='utf-8') as outfile:
        # with open('AlgorithmServer/clovaocr_multi_coordinate.json', 'w', encoding='utf-8') as outfile:
            # json.dump(coordinate, outfile, indent=4, ensure_ascii=False)


# app 구성 영역
@app.route('/', methods=['GET', 'POST'])

# HTTP(S) 수신 함수
def receive():
    if request.method == 'POST':
        ClovaOcr.counter = 0                    # 개인정보 갯수 카운터 초기화
        # 파일이 첨부되어 있는가 확인
        if 'file' not in request.files:
            flash('Not allowed file')
            return redirect(request.url)

        file = request.files['file']
        # 파일 이름 확인 - 예외처리
        # To-DO: Try-Except 구문 사용?
        if file.filename == '':
            flash("None file")
            return redirect(request.url)

        # 파일의 형식 내 확인
        if file and allowed_file(file.filename):
            filename = FILE_DIR + '/' + secure_filename(file.filename)
            file.save(filename)
            basename = os.path.basename(filename)

            with open(FILE_DIR + '/' + str(os.path.splitext(basename)[0])+'_clova_co.json', 'w', encoding='utf-8') as outfile:
                json.dump(ActOnServer.check_privacy(ActOnServer.clova_ocr_response(filename)), outfile, indent=4, ensure_ascii=False)

    return 'Done!'
    '''
    <!doctype html>
    <title>Upload new File</title>
    <h1>Upload new File</h1>
    <form method=post enctype=multipart/form-data>
      <input type=file name=file>
      <input type=submit value=Upload>
    </form>
    '''

# 서버 구동 영역
if __name__ == '__main__':
    app.config['SECRET_KEY'] = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    sess = Session()
    app.run(host='0.0.0.0', port=9000)
