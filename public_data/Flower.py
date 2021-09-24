import json
import pandas as pd
from pprint import pprint
import requests
import xmltodict
from xml.etree import ElementTree
BASE_URL = "http://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerView01?ServiceKey=I%2FKG7I0SlI90upZP19uwP0rAqP3NRDHwwctglxbd%2BLTrMPccjUH9VWXs7%2BbSxY7zDg5uyzF1vmFMM8r9fuF08Q%3D%3D&dataNo="

# 공공데이터 xml -> json으로 변환
def getJsonFlower(i):
    response = requests.get(BASE_URL+f"{i}")
    tree = json.loads(json.dumps(xmltodict.parse(response.content)))
    return tree

if __name__ == "__main__":
    # res_dict 초기화
    res_dict = {
            "fno": [],
            "fnameKR": [],
            "fnameEN": [],
            "fmonth": [],
            "fday": [],
            "flang": [],
            "fcontents": [],
            "fuse": [],
            "fgrow": [],
            "img1": [],
            "img2": [],
            "img3": []
        }

    # 총 366개 데이터 res_dict에 저장
    for i in range(1, 367): 
        res = getJsonFlower(i)
        res_dict["fno"].append( res['document']['root']['result']['dataNo'] )
        res_dict["fnameKR"].append( res['document']['root']['result']['flowNm'] )
        res_dict["fnameEN"].append( res['document']['root']['result']['fEngNm'] )
        res_dict["fmonth"].append( res['document']['root']['result']['fMonth'] )
        res_dict["fday"].append( res['document']['root']['result']['fDay'] )
        res_dict["flang"].append( res['document']['root']['result']['flowLang'] )
        res_dict["fcontents"].append( res['document']['root']['result']['fContent'] )
        res_dict["fuse"].append( res['document']['root']['result']['fUse'] )
        res_dict["fgrow"].append( res['document']['root']['result']['fGrow'] )
        res_dict["img1"].append( res['document']['root']['result']['imgUrl1'] )
        res_dict["img2"].append( res['document']['root']['result']['imgUrl2'] )
        res_dict["img3"].append( res['document']['root']['result']['imgUrl3'] )

    # 최종 데이터 csv파일로 저장.
    final_data = pd.DataFrame(res_dict)
    final_data.to_csv('c:/Users/multicampus/Desktop/flower/flowerdata_test1.csv', encoding='utf-8')

# import json
# import pandas as pd
# from pprint import pprint
# import requests
# import xmltodict
# from xml.etree import ElementTree
# BASE_URL = f"http://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerView01?ServiceKey=I%2FKG7I0SlI90upZP19uwP0rAqP3NRDHwwctglxbd%2BLTrMPccjUH9VWXs7%2BbSxY7zDg5uyzF1vmFMM8r9fuF08Q%3D%3D&dataNo="

# def getJsonFlower(i):
#     response = requests.get(BASE_URL+f"{i}")
#     tree = json.loads(json.dumps(xmltodict.parse(response.content)))
#     return tree

# if __name__ == "__main__":
#     new_data = {
#         "fno": [],
#             "fnameKR": [],
#             "fnameEN": [],
#             "fmonth": [],
#             "fday": [],
#             "flang": [],
#             "fcontents": [],
#             "fuse": [],
#             "fgrow": [],
#             "img1": [],
#             "img2": [],
#             "img3": []
#     }
#     final_data = pd.DataFrame(new_data)
#     for i in range(1, 5):
#         res_dict = {
#             "fno": [],
#             "fnameKR": [],
#             "fnameEN": [],
#             "fmonth": [],
#             "fday": [],
#             "flang": [],
#             "fcontents": [],
#             "fuse": [],
#             "fgrow": [],
#             "img1": [],
#             "img2": [],
#             "img3": []
#         }
#         # print(res_dict)
#         res = getJsonFlower(i)
#         # print(res)
#         res_dict["fno"].append( res['document']['root']['result']['dataNo'] )
#         res_dict["fnameKR"].append( res['document']['root']['result']['flowNm'] )
#         res_dict["fnameEN"].append( res['document']['root']['result']['fEngNm'] )
#         res_dict["fmonth"].append( res['document']['root']['result']['fMonth'] )
#         res_dict["fday"].append( res['document']['root']['result']['fDay'] )
#         res_dict["flang"].append( res['document']['root']['result']['flowLang'] )
#         res_dict["fcontents"].append( res['document']['root']['result']['fContent'] )
#         res_dict["fuse"].append( res['document']['root']['result']['fUse'] )
#         res_dict["fgrow"].append( res['document']['root']['result']['fGrow'] )
#         res_dict["img1"].append( res['document']['root']['result']['imgUrl1'] )
#         res_dict["img2"].append( res['document']['root']['result']['imgUrl2'] )
#         res_dict["img3"].append( res['document']['root']['result']['imgUrl3'] )
#         print(res_dict["fcontents"])
#         final_data.loc[i] = [res_dict["fno"], res_dict["fnameKR"], res_dict["fnameEN"], res_dict["fmonth"], res_dict["fday"], res_dict["flang"], res_dict["fcontents"], res_dict["fuse"], res_dict["fgrow"], res_dict["img1"], res_dict["img2"], res_dict["img3"]]
#         # final_data.append(res_dict, ignore_index=True)
#         print("----------------------------------------------------------")
#         print(final_data)