import easyocr
import numpy as np
# import re
import cv2
import matplotlib.pyplot as plt                 # 이미지 표출시 조작 관련 툴
from PIL import ImageFont, ImageDraw, Image
from flask import Flask, json
from algorithm import Algorithm                 # 개인정보 탐지 알고리즘 관련


# 앱과의 HTTPS 통신 관련
class HttpsWithApp():
    pass

        # image = cv2.cvtColor(origin, cv2.COLOR_BGR2RGB)
class PreProcessing():
    kernel = np.ones((3,3), dtype=np.uint8)          # 3x3 array 내용 1 생성
    # canny(or 라플레시안) / histogram / 
    
    # 1. Canny (or 라플레시안)

    # 2. 외곽선 찾기 - 배경과 주민등록증 구분
    # 3. 회전?

# easyOCR 관련
class EasyOcr():
    # easyocr(인식 언어, gpu 사용 여부), 결과 정보 저장 변수
    reader = easyocr.Reader(['ko', 'en'], gpu=False)
    result = reader.readtext('./AlgorithmServer/dl01.jpg')
    # result = reader.readtext('./AlgorithmServer/SSN.png')

    img = cv2.imread('./AlgorithmServer/registcard_test.png', cv2.IMREAD_GRAYSCALE)
    # img = cv2.imread('./AlgorithmServer/SSN.png', cv2.IMREAD_COLOR)
    
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)              # BRG로 인식하는 openCV -> RGB로 변환해 줘야 함

    # 한글 인식률을 올려주는 Pillow
    img = Image.fromarray(img)
    font = ImageFont.truetype("HMKMRHD.TTF", 11)            # 이미지에 텍스트 표출시 사용할 로컬 폰트
    draw = ImageDraw.Draw(img)

    # px = img.load()                                         # 이미지 픽셀값 저장용 변수
    coordinate = {}                                           # 데이터 저장 사전
    jumin_counter = 0                                         # 개인정보(주민번호) 갯수 카운팅 변수
    license_counter = 0                                       # 개인정보(면허번호) 갯수 카운팅 변수

    # 인식 텍스트 좌표값 추출 함수
    def get_coordinate(result):  
        # bounding box 좌표, 텍스트, 검증(1에 가까울수록 높음) - KITTY, VOC 형식
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
                # EasyOcr.draw.rectangle((tl, br), outline="red")
                # EasyOcr.draw.text((int(tl[0]), int(tl[1])), text, font=EasyOcr.font, fill="blue")
        
            if Algorithm.licensenum_check(text):
                EasyOcr.license_counter += 1
                EasyOcr.coordinate.update({"license {}".format(EasyOcr.license_counter): [{"x": tl[0], "y":tl[1]}, {"x": tr[0], "y":tr[1]}, {"x": br[0], "y":br[1]}, {"x": bl[0], "y":bl[1]}]})
        
        return EasyOcr.coordinate

    # 개인정보 인식 영역 좌표값 .json 파일 추출 함수
    def extract_json(coordinate):
        # with open('./AlgorithmServer/easyocr_coordinate.json', 'w', encoding='utf-8') as outfile:
        with open('./AlgorithmServer/easyocr_tilted_coordinate.json', 'w', encoding='utf-8') as outfile:
            json.dump(coordinate, outfile, indent=4, ensure_ascii=False)


# 실행 영역
if __name__ == "__main__":
    EasyOcr.extract_json(EasyOcr.get_coordinate(EasyOcr.result))
    # plt.imshow(PreProcessing.noise_open('./AlgorithmServer/registcard_test.png'))
    
    # EasyOcr.img.save("./registcard_test_masked.png")                       # 마스킹 작업된 이미지 파일 저장
    # EasyOcr.img.save("./SSN_masked.png")                                   # 마스킹 작업된 이미지 파일 저장
    # plt.imshow(EasyOcr.img)                                                # 마스킹 작업된 이미지 표출
    # plt.show()


'''
# 미 사용 및 예비 영역
# 인식 텍스트 픽셀값 추출 함수
def get_pixel(result):
    for (bbox, text, prob) in result:
        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        if Algorithm.ssn_check(text):
            pixel = [EasyOcr.px[tl]]                    # 픽셀 RGB값
            pixel.clear()                               # tl값이 중복되기 때문에 1회 초기화
        
            for x in range(tl[0], br[0]):               # x좌표
                for y in range(tl[1], bl[1]):           # y좌표를 받아와서
                    pixel.append(EasyOcr.px[x,y])       # 픽셀값 저장 리스트
                    EasyOcr.px[x,y] = (0, 0, 0)         # 사진 픽셀값 0,0,0(Black)으로 변환해 마스킹
    return pixel

# 개인정보 인식 영역 픽셀값 저장 .txt 파일
def extract_pixel_txt(pixel):
    with open("pixel_file.txt", "w", encoding="utf8") as pixel_file:
        print(pixel, file=pixel_file)
'''
