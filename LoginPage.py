from tkinter import*
import tkinter.messagebox as msgbox

main = Tk()
main.title("GTN GUI")
main.geometry("360x640")                # 창 크기 가로*세로+x좌표+y좌표
main.resizable(False, False)            # x, y 크기 변경 불가

frame_login = Frame(main, width=320, height=160, borderwidth=3, relief=GROOVE)
frame_login.place(x=20, y=200)
frame_login.propagate(0)                # item에 따라서 frame이 줄어들지 않음

# 제목 표시 라벨
label = Label(frame_login, text="Password", font=("Arial", 14))
label.pack(pady=20)

# 패스워드 입력 엔트리
entry_password = Entry(frame_login, width=20, show='*', font=("Arial", 14))
entry_password.pack()
password = "1234"                       # 임시 패스워드 - 파일 입력 예정

# 함수 영역
def get_entry_text():
    if entry_password.get() == password:
        msgbox.showinfo("확인", "패스워드가 일치합니다")
        frame_login.destroy()
        create_new_frame()
    else:
        msgbox.askretrycancel("경고", "패스워드가 일치하지 않습니다.")
        entry_password.delete(0, END)           # 내용 삭제

# 프레임 변경 함수 - 임시
def create_new_frame():
    frame_login2 = Frame(main, width=320, height=600, borderwidth=3, relief=GROOVE)
    frame_login2.place(x=20, y=20)
    frame_login2.propagate(0)                # item에 따라서 frame이 줄어들지 않음


button_pw = Button(frame_login, text="확인", width=8, command=get_entry_text)
button_pw.pack(padx=(160, 0), pady=10)


main.mainloop()
