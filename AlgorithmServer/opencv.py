import cv2
import numpy as np


win_name = 'scan'
img = cv2.imread('./test.jpg')
draw = img.copy()

#Gary 스케일 변환 및 캐니 엣지 검출
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
gray = cv2.GaussianBlur(gray, (3, 3), 0)
edged = cv2.Canny(gray, 300, 300)
cv2.imshow(win_name, edged)
cv2.waitKey(0)

#컨투어 찾기
cnts, _ = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

print(len(cnts))
cv2.drawContours(draw, cnts, -1, (0, 255, 0))
cv2.imshow(win_name, draw)
cv2.waitKey(0)

cnts = sorted(cnts, key = cv2.contourArea, reverse = True)[:5]
for c in cnts:
    peri = cv2.arcLength(c, True)   #영역이 가장 큰 컨투어부터 근사 컨투어 단순화
    verticles = cv2.approxPolyDP(c, 0.02 * peri, True)  # 둘레 길이의 0.02 근사값으로 근사화
    if len(verticles) == 4: # 근사한 꼭짓점이 4개면 중지
        break

pts = verticles.reshape(20, 1)   # N * 1 * 2 배열을 4 * 2 크기로 조정
for x, y in pts:
    cv2.circle(draw, (x,y), 10, (0, 255, 0), -1)

cv2.imshow(win_name, draw)
cv2.waitKey(0)
marged = np.hstack((img, draw))

cv2.destroyAllWindows()