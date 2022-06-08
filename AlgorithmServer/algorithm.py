import re

class Algorithm():
    jumin_cond = [0,0,0,0,0]
    license_cond = [0,0,0,0,0]
    regist_cond = [0,0,0,0,0,0]
    
    # 단어 쪼개서 조합하기
    def is_idcard(input):
        Algorithm.jumin_cond = [0, 0, 0, 0, 0]
        word = list(input)
        for i in word:
            if i == "주":
                Algorithm.jumin_cond[0] = 1
            if i == "민":
                Algorithm.jumin_cond[1] = 1
            if i == "등":
                Algorithm.jumin_cond[2] = 1
            if i == "록":
                Algorithm.jumin_cond[3] = 1
            if i == "증":
                Algorithm.jumin_cond[4] = 1
            if Algorithm.jumin_cond == [1, 1, 1, 1, 1]:
                break
        if Algorithm.jumin_cond == [1, 1, 1, 1, 1]:
            return True
        else:
            return False

    def is_license(input):
        Algorithm.license_cond = [0, 0, 0, 0, 0]
        word = list(input)
        for i in word:
            if i == "운":
                Algorithm.license_cond[0] = 1
            if i == "전":
                Algorithm.license_cond[1] = 1
            if i == "면":
                Algorithm.license_cond[2] = 1
            if i == "허":
                Algorithm.license_cond[3] = 1
            if i == "증":
                Algorithm.license_cond[4] = 1
            if Algorithm.license_cond == [1, 1, 1, 1, 1]:
                break
        if Algorithm.license_cond == [1, 1, 1, 1, 1]:
            return True
        else:
            return False

    def is_registration(input):
        Algorithm.regist_cond = [0, 0, 0, 0, 0, 0]
        word = list(input)
        for i in word:
            if i == "주":
                Algorithm.regist_cond[0] = 1
            if i == "민":
                Algorithm.regist_cond[1] = 1
            if i == "등":
                Algorithm.regist_cond[2] = 1
            if i == "록":
                Algorithm.regist_cond[3] = 1
            if i == "표":
                Algorithm.regist_cond[4] = 1
            if i == "본":
                Algorithm.regist_cond[5] = 1
            if Algorithm.regist_cond == [1, 1, 1, 1, 1, 1]:
                break
        if Algorithm.regist_cond == [1, 1, 1, 1, 1, 1]:
            return True
        else:
            return False

    # 주민번호 정규식 판단
    def jumin_check(input):
        ssn = re.compile("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$")

        if ssn.match(input):
            return True
        else:
            return False

    # 운전면허번호 정규식 판단
    def licensenum_check(input):
        drv = re.compile("^(?:[0-9]{1,2})-[0-9]{2}-[0-9]{6}-[0-9]{2}$")
        # drv = re.compile("?<=[^0-9a-zA-Z])(\d{2}[\s-:\.])(\d{6}[\s-:\.])(\d{2})(?=[^0-9a-zA-Z]")
        if drv.match(input):
            return True
        else:
            return False
