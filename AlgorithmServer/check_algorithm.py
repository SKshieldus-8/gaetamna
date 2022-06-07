import easyocr
import numpy as np
import re

class EasyOcr():
    reader = easyocr.Reader(['ko', 'en'], gpu = False)
    result = reader.readtext('./AlgorithmServer/registcard_test.png')
    print(result)


# 텍스트 일부를 인식하여 종류 판단
    result = []
    for i in range(len(result[' '])):
        print.append(result[' '][i]['recognition_words'][0])
        str = ' '.join(print)
        kind = ' '
    if re.match(r'(주민)', str) != None:
        msg = str
        kind = "주민등록증"
    elif re.match(r'(운전)', str) != None:
        msg = str
        kind = "운전면허증"

    else:
        msg = str
        kind = "text"


    # # 주민등록증 판단
    # def is_idcard(input):
    #     if input == "주민등록증":
    #         return True
    #     else:
    #         return False
    
    # 주민번호 정규식 판단
    def ssn_check(input):
        ssn = re.compile("?<=[^0-9a-zA-Z])([0-9][0-9][01][0-9][0-3][0-9][\s-:\.]?)([1-4]\d{6})(?=[^0-9a-zA-Z]")
        if ssn.match(input):
            return True
        else:
            return False

    # # 운전면허증 판단
    # def is_dlcard(input):
    #     if input == "운전면허증":
    #         return True
    #     else:
    #         return False

    # 운전면허번호 정규식 판단
    def ssn_check(input):
        ssn = re.compile("?<=[^0-9a-zA-Z])(\d{2}[\s-:\.])(\d{6}[\s-:\.])(\d{2})(?=[^0-9a-zA-Z]")
        if ssn.match(input):
            return True
        else:
            return False