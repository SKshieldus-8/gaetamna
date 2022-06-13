# # Encryption
# import hashlib
 
# str = "12345678"
# print(hashlib.sha256(str.encode()).hexdigest())



# # Decryption
# import hashlib
 
# str = """11a4a60b518bf24989d481468076e5d5982884626aed9faeb35b8576fcd223e1"""
# for i in range(123):
# 	str = str.replace(hashlib.sha256(chr(i).encode()).hexdigest(), chr(i))
# print(str)

import hashlib


put = input("패스워드 입력 : ")
passwd = ''
result = hashlib.sha256(put.encode())
passwd += result.hexdigest()
result = hashlib.sha256(passwd.encode())



for i in range(1):
    result = hashlib.sha256(passwd.encode())
    print("SHA256 : ",result.hexdigest())

    passwd=''
    passwd += result.hexdigest()