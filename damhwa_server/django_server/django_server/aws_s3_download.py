# AWS library
import boto3

import os
import natsort
import pandas as pd

# s3 설정
s3 = boto3.client('s3')

# 파일 읽기
IMG_DIR = '/Users/youn/Downloads/images'
file_list = os.listdir(IMG_DIR)

# 파일 이름순으로 정렬 (파일 이름을 숫자형식으로 정렬)
file_list = natsort.natsorted(file_list)

# url을 저장할 객체 선언
url_list= {
    "watercolor_img": [],
}

# AWS s3에 업로드된 파일 url 가져오기
for file in file_list:

    # AWS에서 url 직접 호출
    # url = s3.generate_presigned_url(
    #     ClientMethod='get_object',
    #     Params={
    #         'Bucket': 'damhwa-bucket',
    #         'Key': file,
    #     },
    # )

    # url 직접 생성
    url = 'https://damhwa-bucket.s3.ap-northeast-2.amazonaws.com/'+file
    print(url)
    url_list["watercolor_img"].append(url)

# 결과를 CSV 파일로 저장
final_data = pd.DataFrame(url_list)
final_data.to_csv('/Users/youn/Downloads/watercolor_img.csv', encoding='utf-8')