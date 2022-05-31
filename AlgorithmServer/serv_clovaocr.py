import requests
import uuid
import time
from flask import Flask, json
import re
# import numpy as np
# import cv2
# from PIL import ImageFont, ImageDraw, Image


# 앱과의 HTTPS 통신 관련
class HttpsWithApp():
    pass

# 개인정보 탐지 알고리즘 관련
class Algorithm():
    # 주민번호 정규식 판단
    def ssn_check(input):
        ssn = re.compile(
            "^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$")

        if ssn.match(input):
            return True
        else:
            return False

# Naver Clova OCR 관련
class ClovaOcr():
    coordinate = {}                 # 좌표값 저장용 사전
    counter = 0                     # 개인정보 갯수 카운팅 변수
    # 네이버 클로바 ocr api url
    api_url = "https://yny98jcxqj.apigw.ntruss.com/custom/v1/16149/43a5dd752714ac0facb6eed256512ee04bd2a43cd3d7bc43ea6bf31fdc530da0/general"
    # 앱 보안 키(네이버 클로바 키 or 아마존 키)
    secret_key = "dHBtZ0xwQmNuRW5XUWhsS1VDdVJRU0RnREtUVU5BY3c="

    # input_file = './AlgorithmServer/registcard_test.png'
    input_file = './AlgorithmServer/SSN.png'
    # output_file = './AlgorithmServer/clova_single_output.json'
    output_file = './AlgorithmServer/clova_multi_output.json'

    request_json = {
        'images': [
            {
                'format': 'png',
                'name': 'demo'
            }
        ],
        'requestId': str(uuid.uuid4()),         # 무작위 UUID 값으로 requestID 생성
        'version': 'V2',                        # V2로 bounding polygon 정보 제공 받음
        'timestamp': int(round(time.time() * 1000))                 # ms * 1000
    }

    payload = {'message': json.dumps(request_json).encode('UTF-8')}
    files = [
        ('file', open(input_file, 'rb'))
    ]
    headers = {
        'X-OCR-SECRET': secret_key
    }


# Clova OCR를 이용해 서버에서 작동할 함수 관련
class ActOnServer():
    # API 호출에 대한 응답 함수
    def clova_ocr_response():
        response = requests.request("POST", ClovaOcr.api_url, headers=ClovaOcr.headers, data=ClovaOcr.payload, files=ClovaOcr.files)
        res = json.loads(response.text.encode('utf8'))      # 실 내용 저장 변수
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
    def extract_coordinate_json(coordinate):
        # with open('./AlgorithmServer/clovarocr_coordinate.json', 'w', encoding='utf-8') as outfile:
        with open('./AlgorithmServer/clovaocr_multi_coordinate.json', 'w', encoding='utf-8') as outfile:
            json.dump(coordinate, outfile, indent=4, ensure_ascii=False)


# 실행 영역
if __name__ == "__main__":
    # To-Do - response https
    ActOnServer.extract_coordinate_json(ActOnServer.check_privacy(ActOnServer.clova_ocr_response()))
    # To-Do - request https
