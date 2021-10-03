from django.apps import AppConfig
import torch

class KobertmodelConfig(AppConfig):
    name = 'kobertmodel'
    # GPU 사용

    device = torch.device("cpu")
    print(device)
    # 모델 load
    PATH = '/Users/youn/Downloads/kobert/'
    model = torch.load(PATH + 'KoBERT_86.pt', map_location=device)  # 전체 모델을 통째로 불러옴, 클래스 선언 필수
    model.load_state_dict(
        torch.load(PATH + 'model_state_dict_86.pt', map_location=device))  # state_dict를 불러 온 후, 모델에 저장
    print("————————model-load 완료————————")

