import boto3
import os
import natsort

s3 = boto3.client('s3')

IMAGE_DIR = '/Users/youn/Downloads/images'
file_list = os.listdir(IMAGE_DIR)
print(IMAGE_DIR)

# 파일 이름순으로 정렬 (이름을 숫자로 인식하여 정렬)
file_list = natsort.natsorted(file_list)

# file 업로드 
for file in file_list:
    print(file)
    try:
        upload_file = os.path.join(IMAGE_DIR,file)
        s3.upload_file(upload_file,"damhwa-bucket",file)
    except Exception as err:
        print("file upload error", err)

