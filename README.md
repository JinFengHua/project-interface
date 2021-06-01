# project-interface
毕设项目中的接口项目

其中人脸识别的调用在utils.java中，由于face_recognition库打包时一直提示我找不到该模块，所以若想使用该功能请自行创建python项目，导入opencv、dlib、face_recognition，
所调用的python脚本文件内容如下：
import face_recognition
import cv2
import sys

# 首先提高被识别图片的清晰度，以便提高识别率
def recongnition(image,face):
    image = cv2.imread(image)
    small_frame = cv2.resize(image, (0, 0), fx=0.25, fy=0.25)
    image = small_frame[:, :, ::-1]  # 将opencv的BGR格式转换为RGB格式
    face_locations = face_recognition.face_locations(image)
    image_encoding = face_recognition.face_encodings(image, face_locations)
    if len(image_encoding) < 1 :
        return 3
    else:
        img2 = image_encoding[0]

    # 加载考勤的人脸图片
    face_image = face_recognition.load_image_file(face)
    face_locations = face_recognition.face_locations(face_image)
    face_encoding = face_recognition.face_encodings(face_image, face_locations)
    if len(face_encoding) < 1 :
        return 3
    else:
        img1 = face_encoding[0]
    face_encodings = []
    face_encodings.append(img1)

    # 执行人脸识别
    matches = face_recognition.compare_faces(face_encodings, img2, tolerance=0.49)
    if True in matches:
        return 2
    return 1

if __name__ == '__main__':
    # 接收需要进行识别的两个图片的地址
    path1 = sys.argv[1]
    path2 = sys.argv[2]

    result = recongnition(path1, path2)
    print(result)
