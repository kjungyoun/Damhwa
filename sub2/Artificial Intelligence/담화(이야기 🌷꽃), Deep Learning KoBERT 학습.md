# 담화(이야기 🌷꽃), Deep Learning KoBERT 학습

## BERT에 대한 기본적인 설명

- 자연어 처리 분야에서 2019년인가? 갑자기 한 학술지에 미친 성능을 지닌 모델이 나옴. 그게 바로 BERT임
- BERT는 Bidirectional Encoder Representations from Transformer의 약자로 텍스트를 양방향(앞뒤)로 확인하여 자연어를 처리하는 모델이다. 기존의 자연어 처리 모델은 단방향 우리가 글을 읽는 순서인 왼→오로 갔다. 근데 이 녀석은 양방향으로 보기 때문에 다른 모델에 비해 매우 정확하다. 그리고 일단 무엇보다 대형 인공지능의 일종(구글에서 개발)이고 오픈소스이기 때문에 누구나 사용할 수 있는 장점이 있다.
- 일단 위의 링크에서 바로 BERT를 체험해볼 수 있도록 여러 링크를 달아놓았다.
- Colab에서 돌릴 때 웬만하면 GPU 또는 TPU를 켜놓고 하자 안그러면 아에 돌아가질 않는다.
- 그리고 KoBERT라는 것은 한국어의 경우 다른 나라의 언어보다 훨씬 복잡해서 SKT-brain이라는 곳(슼인거 같음)에서 BERT의 한국어 버전을 만들었다. 예측률이 훨씬 좋다고한다.
- 그래서 지금 이 모델로 학습을 진행하고 있다.

## BERT를 선택한 이유

- 구글에서 BERT하는 자연어 처리 Deep Learning 모델이 나왔는데, 이게 기존에 나왔던 자연어 처리 모델인 BiLSTM, GPT 보다 훨씬 나은 성능을 보여주었습니다. 특히 BERT의 B는 Bidirectional로 텍스트를 양방향으로 분석할 수 있기에, 인간이 문서를 작성할 때 나타나는 논리적인 흐름도 인공지능이 학습할 수 있다는 장점이 있습니다. 따라서 이러한 성능을 활용하는 것이 보다 나은 서비스를 만들 수 있을것으로 팀원들과 판단했으며, BERT를 저희 담화 서비스에 접목시키는 것으로 결정했습니다.

## KoBERT를 사용한 이유

- BERT는 104개 정도나 되는 언어를 선택하여 학습을 시킬 수 있도록 모델을 제공해주고 있습니다. 즉 BERT를 활용할 때는 기존 구글에서 언어의 특징을 학습시킨 BERT를 모델을 이용하여 Fine 튜닝을 진행할 수 있습니다. 여기서 구글이 학습시킨 BERT는 input과 output 출력을 지닌 것이 아니라, 언어 자체의 특징을 학습시킨 BERT 모델입니다. 이를 활용해서 output을 지닌 모델로 재탄생시키는 것이 저희팀이 이번 주에 목표로 잡은 테스크입니다. 이때 한국어를 BERT 모델에 적용할 때 한계점이 나타납니다. 왜냐하면 한국어는 다른 언어보다 너무 복잡하기 때문입니다. 이에 이러한 BERT의 문제점을 극복하고자 나온 모델이 바로 KoBERT입니다. KoBERT는 기존에 BERT보다 훨씬 더 많은 한국어를 학습시킨 BERT 모델의 일종으로 한국어를 분석할 때는 기존의 BERT보다 더 나은 성능을 보여주고 있기에, 이를 사용하기로 결정했습니다.

## 활용 데이터

- AI hub에 올라온 감정이 담긴 말뭉치 데이터를 사용했습니다. 약 7만 개의 Training 데이터를 이용하여 유저가 텍스트를 적으면 해당 텍스트에 알맞는 감정을 분류하는 모델을 구축하고 있습니다.

---

사람의 감정을 6개로 대분류하여 이를 KoBERT 모델로 학습한 초기 결과 워낙 큰 모델이기 때문에, 처음 학습시킬 당시 1 epoch를 시행했을 때 20분 이상이 소요되었습니다. 따라서 많은 epoch를 지정하여 학습시킬 수 없다는 판단을 내렸고, 5 epoch로 학습시킨 결과 약 70% 정확도를 보여주었던 KoBERT모델이지만, 10 epoch로 약 4시간 정도 학습 시킨 결과 테스트 데이터의 정확도가 86%로 상승한 것을 확인할 수 있었습니다.

```bash
# pip install
!pip install mxnet
!pip install gluonnlp pandas tqdm
!pip install sentencepiece
!pip install transformers==3.0.2
!pip install torch
```

- colab으로 pip를 진행하고 싶으면 앞에 !를 붙이면 됩니다.

```python
# torch 로드
import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np
from tqdm import tqdm, tqdm_notebook
```

- pytorch 기반 KoBERT 학습을 위해 torch와 관련 라이브러리를 모두 로드해줍니다.

```python
#kobert 로드
from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

#transformers 로드
from transformers import AdamW
from transformers.optimization import get_cosine_schedule_with_warmup
```

- 이제 KoBERT와 transformers를 로드합니다.

```python
#GPU 사용, colab환경에서 CPU보다 GPU를 사용하는 것을 선호하기 때문에 이를 활용합니다.
device = torch.device("cuda:0")
```

```python
# 사용 가능한 GPU 탐색 후 해당 GPU의 이름과 갯수를 함께 출력
import os

n_devices = torch.cuda.device_count()
print(n_devices)

for i in range(n_devices):
    print(torch.cuda.get_device_name(i))
```

```python
#BERT 모델, Vocabulary 불러오기
bertmodel, vocab = get_pytorch_kobert_model()
```

```python
#구글드라이브 연동, 보다 빠르게 구동시키기 위해서 google drive를 함께 사용합니다.
from google.colab import drive
drive.mount('/content/drive')
```

```python
# 데이터를 불러옵니다. AIhub에서 엑셀파일을 제공해주고 있으므로 이를 활용합니다.
import pandas as pd
train_data = pd.read_excel('drive/MyDrive/colab/StoryFlower/bert/감성대화말뭉치(최종데이터)_Training.xlsx')
test_data = pd.read_excel('drive/MyDrive/colab/StoryFlower/bert/감성대화말뭉치(최종데이터)_Validation.xlsx')
```

```python
# 감정 대분류의 종류를 알기 위해 다음과 같이 코드를 작성합니다.
# 이때 이상한 데이터가 있을 수 있음을 유의하면서 이를 직접확인해야 합니다.
# 데이터의 단어에 빈공간이 있을 수 있으므로 이를 없애기 위해 strip()을 사용하여
# 빈 공간을 제거합니다.
emotion_class = list(train_data["감정_대분류"])
emo_dic = {}
idx = 0
for emo in emotion_class:
  if emo.strip() in emo_dic.keys(): continue
  else:
    emo_dic[emo.strip()] = int(idx)
    idx += 1

print(emo_dic)
```

```python
# 존재하는 감정들을 Int형으로 바꾸어줍니다. 이때 그 기준은 바로 위에서 만들었던 emo_dic을 근거로 해당 분류에 따른 index값을 넣어줍니다.
for key in emo_dic.keys():
    train_data.loc[(train_data["감정_대분류"] == key), "Emotion"] = emo_dic[key]
    test_data.loc[(test_data["감정_대분류"] == key), "Emotion"] = emo_dic[key]
```

```python
dataset_train = []
for q1, q2, label in zip(train_data['사람문장1'], train_data['사람문장2'], train_data['Emotion']):
    data = []
    txtEmotion = q1 + " " + q2
    data.append(txtEmotion)
    data.append(str(label)[0])
    dataset_train.append(data)

dataset_test = []
for q1, q2, label in zip(test_data['사람문장1'], test_data['사람문장2'], test_data['Emotion']):
    data = []
    txtEmotion = q1 + " " + q2
    data.append(txtEmotion)
    data.append(str(label)[0])
    dataset_test.append(data)
```

- 위의 코드는 모형에 입력될 데이터를 정리하는 코드입니다. 사람문장1,2와 Emotion만 학습에 필요하므로 이를 활용합니다.

```python
# BERT 클래스를 만듭니다.
class BERTDataset(Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer, max_len,
                 pad, pair):
        transform = nlp.data.BERTSentenceTransform(
            bert_tokenizer, max_seq_length=max_len, pad=pad, pair=pair)

        self.sentences = [transform([i[sent_idx]]) for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        return (self.sentences[i] + (self.labels[i], ))

    def __len__(self):
        return (len(self.labels)) 
```

```python
# 파라미터를 세팅합니다.
# 학습 시간이 오래걸리기 때문에, epoch를 10으로 설정합니다.
max_len = 64
batch_size = 64
warmup_ratio = 0.1
num_epochs = 10
max_grad_norm = 1
log_interval = 100
learning_rate =  5e-5
```

```python
# 토큰화 nlp를 진행하려면 해당 텍스트의 토큰화를 진행해야 합니다. 텍스트 → 수치화
tokenizer = get_tokenizer()
tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)

data_train = BERTDataset(dataset_train, 0, 1, tok, max_len, True, False)
data_test = BERTDataset(dataset_test, 0, 1, tok, max_len, True, False)
```

```python
# torch에 데이터를 로드시킵니다.
train_dataloader = torch.utils.data.DataLoader(data_train, batch_size=batch_size, num_workers=5)
test_dataloader = torch.utils.data.DataLoader(data_test, batch_size=batch_size, num_workers=5)
```

```python
## BERT 분류기를 만듭니다.
## 이때 DeepLearning 모델의 구조가 결정됩니다.
class BERTClassifier(nn.Module): ## 클래스를 상속
    def __init__(self,
                 bert,
                 hidden_size = 768,
                 num_classes = 6,   ##클래스 수 조정##
                 dr_rate=None,
                 params=None):
        super(BERTClassifier, self).__init__()
        self.bert = bert
        self.dr_rate = dr_rate
                 
        self.classifier = nn.Linear(hidden_size , num_classes)
        if dr_rate:
            self.dropout = nn.Dropout(p=dr_rate)
    
    def gen_attention_mask(self, token_ids, valid_length):
        attention_mask = torch.zeros_like(token_ids)
        for i, v in enumerate(valid_length):
            attention_mask[i][:v] = 1
        return attention_mask.float()

    def forward(self, token_ids, valid_length, segment_ids):
        attention_mask = self.gen_attention_mask(token_ids, valid_length)
        
        _, pooler = self.bert(input_ids = token_ids, token_type_ids = segment_ids.long(), attention_mask = attention_mask.float().to(token_ids.device))
        if self.dr_rate:
            out = self.dropout(pooler)
        return self.classifier(out)
```

```python
# BERT 모델 불러옵니다. 이때 colab의 GPU를 사용할 수 있도록 device를 활용합니다.
model = BERTClassifier(bertmodel,  dr_rate=0.5).to(device)

# optimizer와 schedule 설정합니다 . 설정하지 않으면 모델이 돌아지 않습니다.
no_decay = ['bias', 'LayerNorm.weight']
optimizer_grouped_parameters = [
    {'params': [p for n, p in model.named_parameters() if not any(nd in n for nd in no_decay)], 'weight_decay': 0.01},
    {'params': [p for n, p in model.named_parameters() if any(nd in n for nd in no_decay)], 'weight_decay': 0.0}
]

# 사용할 초기값 알고리즘은 Adam입니다.
optimizer = AdamW(optimizer_grouped_parameters, lr=learning_rate)
loss_fn = nn.CrossEntropyLoss()

t_total = len(train_dataloader) * num_epochs
warmup_step = int(t_total * warmup_ratio)

scheduler = get_cosine_schedule_with_warmup(optimizer, num_warmup_steps=warmup_step, num_training_steps=t_total)

#정확도 측정을 위한 함수 정의합니다. 모델 학습 도중에 성능을 실시간으로 확인할 수 있도록 합니다.
def calc_accuracy(X,Y):
    max_vals, max_indices = torch.max(X, 1)
    train_acc = (max_indices == Y).sum().data.cpu().numpy()/max_indices.size()[0]
    return train_acc
    
train_dataloader
```

```python
# 학습을 시작합니다.
for e in range(num_epochs):
    train_acc = 0.0
    test_acc = 0.0
    # model에서 학습을 시킨다는 의미를 전달하기 위해 .train()을 붙여줍니다. 이때 만약 붙이지 않는다면, train이 아닌 eval로 작동합니다. torch의 경우 그렇게 작동합니다. 이를 유의해야합니다.
    model.train()
    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(tqdm_notebook(train_dataloader)):
        optimizer.zero_grad()
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)
        valid_length= valid_length
        label = label.long().to(device)
        out = model(token_ids, valid_length, segment_ids)
        loss = loss_fn(out, label)
        loss.backward()
        torch.nn.utils.clip_grad_norm_(model.parameters(), max_grad_norm)
        optimizer.step()
        scheduler.step()  # Update learning rate schedule
        train_acc += calc_accuracy(out, label)
        if batch_id % log_interval == 0:
            print("epoch {} batch id {} loss {} train acc {}".format(e+1, batch_id+1, loss.data.cpu().numpy(), train_acc / (batch_id+1)))
    print("epoch {} train acc {}".format(e+1, train_acc / (batch_id+1)))
    
    model.eval()
    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(tqdm_notebook(test_dataloader)):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)
        valid_length= valid_length
        label = label.long().to(device)
        out = model(token_ids, valid_length, segment_ids)
        test_acc += calc_accuracy(out, label)
    print("epoch {} test acc {}".format(e+1, test_acc / (batch_id+1)))
```

KoBERT 학습이 끝났습니다.