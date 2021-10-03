from torch.utils.data import Dataset
from manage import *


def new_softmax(a):
    c = np.max(a)  # 최댓값
    exp_a = np.exp(a - c)  # 각각의 원소에 최댓값을 뺀 값에 exp를 취한다. (이를 통해 overflow 방지)
    sum_exp_a = np.sum(exp_a)
    y = (exp_a / sum_exp_a) * 100
    return np.round(y, 3)


# 예측 모델 설정
def predict(predict_sentence):
    print('predict 성공')
    data = [predict_sentence, '0']
    dataset_another = [data]
    # GPU 사용
    device = torch.device("cpu")
    print(device)
    # 토큰화
    bertmodel, vocab = get_pytorch_kobert_model()
    tokenizer = get_tokenizer()
    tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)
    # Setting parameters
    max_len = 64
    batch_size = 32
    warmup_ratio = 0.1
    num_epochs = 20
    max_grad_norm = 1
    log_interval = 100
    learning_rate = 5e-5
    print("————————parameter 세팅 완료————————")

    # 모델 load
    PATH = '/Users/youn/Downloads/kobert/'
    model = torch.load(PATH + 'KoBERT_86.pt', map_location=device)  # 전체 모델을 통째로 불러옴, 클래스 선언 필수
    model.load_state_dict(
        torch.load(PATH + 'model_state_dict_86.pt', map_location=device))  # state_dict를 불러 온 후, 모델에 저장
    print("————————model-load 완료————————")
    another_test = BERTDataset(dataset_another, 0, 1, tok, max_len, True, False)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=5)

    model.eval()

    for token_ids, valid_length, segment_ids, label in test_dataloader:
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)

        valid_length = valid_length
        label = label.long().to(device)

        out = model(token_ids, valid_length, segment_ids)

        for i in out:
            logits = i
            logits = logits.detach().cpu().numpy()
            probability = []
            logits = np.round(new_softmax(logits), 3).tolist()
            for logit in logits:
                print(logit)
                probability.append(np.round(logit, 3))

            if np.argmax(logits) == 0:
                emotion = "기쁨"
            elif np.argmax(logits) == 1:
                emotion = "불안"
            elif np.argmax(logits) == 2:
                emotion = '당황'
            elif np.argmax(logits) == 3:
                emotion = '슬픔'
            elif np.argmax(logits) == 4:
                emotion = '분노'
            elif np.argmax(logits) == 5:
                emotion = '상처'

            probability.append(emotion)
            print(probability)
    return probability

    # # 데이터 시연
    # end = 1
    # while end == 1:
    #     sentence = input("하고싶은 말을 입력해주세요 : ")
    #     if len(sentence) < 5:
    #         break
    #     predict(sentence)
    #     print("\n")