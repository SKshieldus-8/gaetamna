import re

class Algorithm():
    # 주민번호 정규식 판단
    def ssn_check(input):
        ssn = re.compile("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$")

        if ssn.match(input):
            return True
        else:
            return False
