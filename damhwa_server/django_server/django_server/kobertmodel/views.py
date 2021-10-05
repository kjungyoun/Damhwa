# rest_framework
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.db import connection
import numpy as np
import pandas as pd
import sys
from os import path
# Message Recommend
@api_view(['POST', 'GET'])
def msg_recomm(request):
    if request.method == 'POST':
        print("Django Success!")
        data = request.data.get('msg') # Spring 요청 데이터
        print("request data : " + data)

        # KoBert 감정 분석 모델
        # model_result = [21.45123, 10.1234, 4.012312, 4.01234, 31.43234, 13.123415]
        sys.path.append(path.join(path.dirname(__file__), '..'))
        from kobert_predict import predict
        model_result = predict(data)

        # knn 알고리즘
        flag = True
        datas = knn(model_result, flag)
        print (datas)

        return Response(data=datas, status=status.HTTP_200_OK)

# State Recommend
@api_view(['POST'])
def state_recomm(request):
    if request.method == 'POST':
        print("Django Success!")
        data = request.data.get('state') # Request data
        print("request data : " + data)

        # KoBert 감정 분석 모델 load
        sys.path.append(path.join(path.dirname(__file__), '..'))
        from kobert_predict import predict
        model_result = predict(data)
        state = model_result[6]

        # knn 알고리즘
        flag = False
        datas = knn(model_result, flag)
        response = {
            'fno': datas,
            'state' : state
        }
        print(datas)

        return Response(data=response, status=status.HTTP_200_OK)

def knn(model_result, flag):
    # DB emotion 조회
    try:
        cursor = connection.cursor()
        strSql = "SELECT fno, happy, unstable, embarrass, sad, angry, hurt FROM emotion"
        result = cursor.execute(strSql)
        emotion = cursor.fetchall()

        connection.commit()
        connection.close()

        datas = []
        for data in emotion:
            # DB 확률값만 저장
            tmp = [data[1], data[2], data[3], data[4], data[5], data[6]]

            # 유클리디안 distance
            sum = 0
            for i in range(0, len(tmp)):
                df = model_result[i] - tmp[i]  # 배열간 뺄셈
                df = df ** 2  # 데이터의 제곱
                sum += df

            row = {
                'fno': data[0],  # flower primary key
                'distance': np.sqrt(sum)  # 데이터들의 합의 제곱근 = 거리
            }

            datas.append(row)

        df1 = pd.DataFrame(datas,columns=['fno','distance']) # 결과 dataframe 생성
        df1 = df1.sort_values('distance').head(5) # distance가 가장 작은 순으로 정렬 후 상위 5개 추출
        print(df1)

        # 상위 5개 fno list로 추출
        result_fno = []
        for index, row in df1.iterrows():
            result_fno.append(int(row['fno']))

        if(flag):
            return result_fno
        else:
            return result_fno[0]
    except:
        connection.rollback()
        print("Failed selecting in emotion")



