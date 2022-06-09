import easyocr
from algorithm import Algorithm                 # 개인정보 탐지 알고리즘 관련


# easyOCR 관련
class EasyOcr():
    reader = easyocr.Reader(['ko', 'en'], gpu=False)

    # 인식 텍스트 좌표값 추출 함수 / ocr결과, 값저장 사전, 검증 리스트1, 검증 리스트2, 검증 리스트3, 카운팅 변수1, 카운팅 변수2
    def get_coordinate(result, dict1, list1, list2, list3, cnt1, cnt2):
        # bounding box 좌표, 텍스트, 검증(1에 가까울수록 정확도 높음)
        # KITTY, VOC 형식
        for (bbox, text, prob) in result:
            # top left, top right, bottom right, bottom left
            (tl, tr, br, bl) = bbox
            tl = (int(tl[0]), int(tl[1]))
            tr = (int(tr[0]), int(tr[1]))
            br = (int(br[0]), int(br[1]))
            bl = (int(bl[0]), int(bl[1]))

            if Algorithm.is_idcard(text, list1):
                dict1.update({"tag": "idcard"})

            if Algorithm.is_license(text, list2):
                dict1.update({"tag": "license"})

            if Algorithm.is_registration(text, list3):
                dict1.update({"tag": "registration"})

            if Algorithm.jumin_check(text):
                cnt1 += 1
                dict1.update({"jumin {}".format(cnt1): [{'x': tl[0], 'y':tl[1]}, {
                    'x': tr[0], 'y':tr[1]}, {'x': br[0], 'y':br[1]}, {'x': bl[0], 'y':bl[1]}]})

            if Algorithm.licensenum_check(text):
                cnt2 += 1
                dict1.update({"license {}".format(cnt2): [{'x': tl[0], 'y':tl[1]}, {
                    'x': tr[0], 'y':tr[1]}, {'x': br[0], 'y':br[1]}, {'x': bl[0], 'y':bl[1]}]})

        return dict1
