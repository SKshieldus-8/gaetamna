import os
import re
from flask import Flask, flash, request, redirect, url_for, json, jsonify
from werkzeug.utils import secure_filename
# import requests
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

# easyOCR 관련
class EasyOcr():
    reader = easyocr.Reader(['ko', 'en'], gpu=False)
    coordinate = {}                                         # 좌표값 저장용 사전
    counter = 0                                             # 개인정보 갯수 카운팅 변수

    # 인식 텍스트 좌표값 추출 함수
    def get_coordinate(result):
        # bounding box 좌표, 텍스트, 검증(???) - KITTY, VOC 형식
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

    # 개인정보 인식 영역 좌표값 .json 파일 추출 함수
    def extract_json(coordinate):
        pass
        # with open('./AlgorithmServer/easyocr_coordinate.json', 'w', encoding='utf-8') as outfile:
        # with open('./AlgorithmServer/easyocr_multi_coordinate.json', 'w', encoding='utf-8') as outfile:
            # json.dump(coordinate, outfile, indent=4, ensure_ascii=False)


# app 구성 영역
@app.route('/', methods=['GET', 'POST'])

# HTTP(S) 수신 함수
def receive():
    try:
        if request.method == 'POST':
            EasyOcr.counter = 0                         # 개인정보 갯수 카운터 초기화
            # 파일이 첨부되어 있는가 확인
            if 'file' not in request.files:
                flash('Not allowed file')
                return redirect(request.url)

            file = request.files['file']
            # 파일 이름 확인 - 예외처리  To-Do: Try-Except 구문 사용?
            if file.filename == '':
                flash("None file")
                return redirect(request.url)

            if file and allowed_file(file.filename):
                filename = FILE_DIR + '/' + secure_filename(file.filename)
                file.save(filename)
                parsed = EasyOcr.reader.readtext(filename)
                # basename = os.path.basename(filename)
                os.remove(filename)
                return json.dumps(EasyOcr.get_coordinate(parsed), ensure_ascii=False, sort_keys=True)

                # with open(FILE_DIR + '/' + str(os.path.splitext(basename)[0])+'_easyocr_co.json', 'w', encoding='utf-8') as outfile:
                    # json.dump(EasyOcr.get_coordinate(parsed), outfile, indent=4, ensure_ascii=False)
    except:
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

# 서버 구동 영역
if __name__ == '__main__':
    app.config['SECRET_KEY'] = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    sess = Session()
    app.run(host='0.0.0.0', port=9000)
