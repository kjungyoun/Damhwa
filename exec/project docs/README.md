# 📝 Build & 배포 문서

## 📑 버전 정보 및 설정 정보

- JVM 버전 :  1.8
- 웹 서버 : AWS 배포
- WAS : Apache Tomcat
- Intellij : 2020.2
- PyCharm : 2021.2
- Kotlin : 1.5.21
- Android : min SDK 24
- Swift : 5

## 📖 Build 및 배포 코드

### ✏️ Spring

- Spring 프로젝트 안에 Dockerfile 파일을 만들고 아래 코드를 입력한다.

```java
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

- gradlew 파일이 있는 디렉터리에서 아래 명령어를 차례대로 입력한다.

```bash
$ sudo ./gradlew clean

$ sudo ./gradlew build
```

- 다음 build 디렉터리가 있는 위치(spring 프로젝트의 최상단)에서 아래 명령어를 입력한다.

```bash
$ docker build -t [이미지이름] .
```

- 다음 코드를 계속해서 순서대로 입력한다.
- 여기서 도커허브 아이디란 이메일이 아닌 Docker의 아이디를 의미한다.

```bash
$ docker tag [이미지 이름]:latest [도커허브아이디]/[이미지이름]:latest

$ docker push [도커허브아이디]/[이미지이름]:latest
```

- 위는 로컬에서 진행한 것이고 이제 EC2 서버에서 진행한다.
- 무중단 실행시 포트번호는 반드시 도커 포트와 이미지 포트 2개를 설정해야지 정상 실행된다!

```bash
# EC2 서버에서
$ sudo docker pull [도커허브아이디]/[이미지이름]:latest

# 무중단 실행
$ docker run -d -p [사용할 포트] [도커이미지Name]

# 예시 (앞포트 = 도커 포트 / 뒷포트 = 이미지 포트)
$ sudo docker run -d -p 8080:8080 youn0729/damhwa:latest
```

### ✏️ Django Version

```
Django 프로젝트를 Docker Image로 빌드하여 EC2 인스턴스에 배포하는 것
Docker 설치, Docker Hub 계정 생성, Django 프로젝트 생성이 되어 있어야 한다.
```

- 마이그레이션 진행한다.

```bash
python manage.py makemigrations

pythom manage.py migrate
```

- requirements.txt 를 생성한다.
- 즉 pip로 install 된 환경을 txt 파일에 저장하여 Docker에서도 동일하게 구성하도록 하는 것이다.

```bash
pip freeze > requirements.txt
```

### ✏️ Dockerfile 작성

- Django 프로젝트 최상단에 Dockerfile 이라는 이름으로 파일을 작성한다. (반드시 파일명이 틀리면 안됨!)
- Django 프로젝트 최상단이라는 것은 다시 말해서 manage.py가 있는 경로를 말하는 것이다.

```python
FROM python:3 # 생성하는 Docker의 python 버전
ENV PYTHONUNBUFFERED 1
RUN apt-get -y update
RUN apt-get -y install vim # Docker 안에서 vi 설치 안해도 됨

RUN mkdir /srv/docker-server # Docker안에 srv/docker-server 폴더 생성
ADD . /srv/docker-server # 현재 디렉터리를 srv/docker-server 폴더에 복사

WORKDIR /srv/docker-server # 작업 디렉터리 설정

RUN pip install --upgrade pip # pip 업그레이드
RUN apt-get install build-essential
RUN pip install --upgrade pip setuptools wheel # Transformers 설치 에러 방지
RUN pip install -r requirements.txt --no-cache-dir # Python torch 설치 에러 방지

EXPOSE 8000 # 8000 port 개방
CMD ["python","manage.py","runserver","0.0.0.0:8000"] # 실행
```

### ✏️ Docker image 생성

- 현재 경로에서 Docker image build를 진행한다.

```bash
docker build -t [생성하고자 하는 도커 이미지이름] .
```

- 이미지가 정상적으로 만들어졌는지 확인하기 위해 docker images를 입력

```bash
docker images
```

- 이제 제대로 돌아가는지 한번 돌려보자

```bash
docker run -p [호스트포트]:[컨테이너포트] [생성한 이미지 이름]

# 예시
docker run -p 8000:8000 damhwa_django
```

- 만약 백그라운드에서 실행하고 싶을 때는 아래처럼 한다.

```bash
docker run -d -p [호스트포트]:[컨테이너포트] [생성한 이미지 이름]

# 예시
docker run -d -p 8000:8000 damhwa_django
```

- 만약 위에서 제대로 실행 되었다면 이제 Docker 허브에 push 해서 EC2 서버에서 불러와보자

### 📌 Docker Hub 에 Image 파일 Push 하기

- Docker Hub 계정이 반드시 있어야 한다.
- 아래 명령어로 push 해보자

```bash
docker tag [생성한 이미지 이름]:latest [도커허브아이디]/[생성한 이미지 이름]:latest

docker push [도커허브아이디]/[생성한 이미지 이름]:latest
```

- 이제 Docker 허브를 확인해보면 해당 이미지가 올라가있을 것이다.

### 📌 EC2 서버에서 pull 받아 실행하기

- 이제 Docker Hub에서 이미지를 가져와 백그라운드로 실행시키면 끝!
- 사용할 컨테이너의 별칭을 지정해주면 해당 컨테이너의 ID를 내가 지정한 별칭으로 사용할 수 있다.

```bash
docker pull [도커허브아이디]/[생성한 이미지 이름]:latest

docker run -d -p [호스트포트]:[컨테이너포트] [생성한 이미지 이름]

docker run -d -p 8000:8000 --name [사용할 컨테이너별칭][생성한 이미지 이름]

# 예시
docker run -d -p 8000:8000 damhwa_django
```

## ⚠️ 배포 시 특이사항

- Django 서버와 Spring 서버 둘 다 배포
- Django 서버 포트 : 8000
- Spring 서버 포트 : 8080
- Docker Hub로 배포 성공

## 📜 DB 정보

- 배포용 DB 정보

  -> Hostname(접속 주소) : 3.38.83.30 / 도메인 : j5a503.p.ssafy.io

  -> Username : ssafy

  -> UserPassword : ssafy
  
  ![스크린샷 2021-10-05 오후 10 18 49](https://user-images.githubusercontent.com/48318620/136031000-7f4530d3-8496-4a4c-b4c7-9dc3f35c05b0.png)


- ER-Diagram

    <img width="901" alt="스크린샷 2021-10-05 오후 10 23 16" src="https://user-images.githubusercontent.com/48318620/136031526-93cf4322-3d21-4dd1-b8bf-a506ea143bec.png">

    
## 📌 인공지능 Model

- SKT Brain KoBERT Model : 한국어 버전의 자연어 처리 모델
- 본 프로젝트에서 진행한 학습은 기존에 학습된 KoBERT 모델에 감정 언어를 주입했던 Fine Tuning이다.
- AIhub에 올라와 있는 약 7만여개의 한국어 말뭉치 데이터를 학습하여 총 6개의 감정 대분류 모형을 만들었다.
- test데이터로 성능을 측정한 결과 약 86%의 정확도를 지니고 있다.
- KoBERT를 이야기 하기전에 BERT에 대해서 조금 더 설명하자면 아래와 같다.

#### 🤖 BERT에 대한 기본적인 설명

- 자연어 처리 분야에서 2019년에 한 학술지에 엄청난 성능을 지닌 모델이 나왔으며. 그게 바로 BERT이다.
- BERT는 Bidirectional Encoder Representations from Transformer의 약자로 텍스트를 양방향(앞뒤)로 확인하여 자연어를 처리하는 모델이다. 기존의 자연어 처리 모델은 단방향 우리가 글을 읽는 순서인 왼쪽→오른쪽으로 갔지만 BERT는 이 순서를 양방향으로 보기 때문에 다른 모델에 비해 매우 높은 정확도를 나타낸다. 그리고 무엇보다 대형 인공지능의 일종(구글에서 개발)이고 오픈소스이기 때문에 누구나 사용할 수 있는 장점이 있다.
- 본 특화 프로젝트에서는 인공지능 모델 구축에서 Colab을 활용했고 GPU를 통해 모델 학습에 걸린 시간은 2시간이며 하나의 모델을 구축하는데 많은 시간이 걸린다. 따라서 Colab 환경에서 지원하는 GPU 또는 TPU를 사용하자. CPU로 연산을 수행하면 아에 진행되지 않음을 느낀다.
- 그리고 KoBERT라는 것은 한국어의 경우 다른 나라의 언어보다 훨씬 복잡해서 SKT-brain에서 BERT의 한국어 버전을 만들었다. 예측률이 훨씬 좋다고한다. 특히 관련 이슈를 [https://github.com/SKTBrain/KoBERT/pulls?q=is%3Apr+is%3Aclosed](https://github.com/SKTBrain/KoBERT/pulls?q=is%3Apr+is%3Aclosed) 에서 검색하여 해결할 수 있으니 참고하면 된다.
- BERT를 학습하기 위해서 사용한 라이브러리는 `kobert, pytorch`이다.

##### ✏️ Colab환경에서 KoBERT 학습하기

```bash
!pip install mxnet # 코랩 환경이기 때문에 앞에 !를 붙여야 한다.
!pip install gluonnlp pandas tqdm
!pip install sentencepiece
!pip install transformers==3.0.2
!pip install torch
```

KoBERT를 이용하기 위해서는 기본적으로 위와 같은 라이브러리가 있어야 한다. 특히 KoBERT에서는  mxnet, torch, gluonnlp를 필히 다운로드 받아야 하며, BERT 모델 공통적으로 transformers 라이브러리가 있어야 한다. 또한 python 버전에 따라 작동하는 transformers의 버전이 상이하므로 이를 주의하자. 본 특화 프로젝트에서는 python==3.7.x에 transformers==3.0.2를 사용했다. 그래서 위와 같이 다운로드를 받아준다.

또한 만약에 내가 KoBERT를 이용하는 것이면

```bash
!pip install git+https://git@github.com/SKTBrain/KoBERT.git@master
```

위의 코드로 KoBERT 라이브러리를 사용하기 위한 패키지를 다운로드 받아야한다.

```python
import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np
from tqdm import tqdm, tqdm_notebook
```

필요한 함수를 모두 load해준다. 나도 위에 있는 모든 함수의 의미를 알지는 못한다. 단지 KoBERT를 이용하기 위해 앞에서 미리 설정해야 할 함수들이다.

```python
#kobert
from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

#transformers
from transformers import AdamW # 인공지능 모델의 초기값 지정 함수를 아담으로 지정한다.
from transformers.optimization import get_cosine_schedule_with_warmup
```

kobert 부분은 우리가 학습할 Kobert 모델을 불러오기 위해 설정하는 것이다. 아니 우리가 인공지능을 만다는데, 왜 저런걸 설정해? 라고 생각한다면, 일단 BERT 모형 자체가 오픈소스다. 이미 구글에서 104개의 언어를 구분해서 학습한 언어 모델이다. 그렇다 이미 학습했다. 그걸 우리가 갖져다가 쓰는거다. 그러면 무엇을 학습했는가? 아마 학 나라의 언어별 특징을 학습하지 않았나 생각든다. 공식문서 들어가서 확인해보면 되겠다.

그리고 BERT 모형은 활성화 함수를 softmax함수를 사용한다. 그래서 입력값으로 인해 출력된 값은 모두 0~1사이의 값이고 다 더 했을 때 1이된다. 그렇다 그냥 확률이다.

(엄밀히 확률이냐? 라고 했을 때 그렇게 봐도 무방하다. 어차피 인공지능도 weight부분을 모수로 추정한다. 그리고 이를 경사하강법을 통해 최적의 weight을 찾는건데, 이때 이 weight을 확률변수로 본다. 그렇다 그냥 확률이다.)

```python
#GPU 사용
device = torch.device("cuda:0")
```

인공지능은 GPU없으면 그냥 덤프와도 같다. 연산이 생명 CPU로 하지말자 1년이 넘어도 안끝날 수 있다. 그니까 GPU 사용을 설정해주자. 주변 이야기로는 TPU를 쓰라고 하는데, TPU를 쓰면 코드가 좀 달라진다. 그래서 일부러 GPU로 먼저 체험하는 식으로 사용해보자.

눈치 빠른 사람을 알겠지만, GPU는 Colab 안에 있다.

```python
import os

n_devices = torch.cuda.device_count()
print(n_devices)

for i in range(n_devices):
    print(torch.cuda.get_device_name(i))
```

`cuda.device_count()`가 현재 사용하는 GPU 개수좀 알려달라는 건데, 저게 0이 뜨면 GPU안쓰고 있는거다. 그니까. 꼭 확인해보고 안뜨면, 왼쪽 상단에 런타임 -> 런타임 환경 변경 -> GPU로 변경해주자.

```python
if torch.cuda.is_available():    
    device = torch.device("cuda")
    print('There are %d GPU(s) available.' % torch.cuda.device_count())
    print('We will use the GPU:', torch.cuda.get_device_name(0))
else:
    device = torch.device("cpu")
    print('No GPU available, using the CPU instead.')
```

그렇다면, 마지막으로 GPU 사용가능한지 체크해보고 GPU의 이름을 볼 수 있도록 세팅하자.

```python
#BERT 모델, Vocabulary 불러오기
bertmodel, vocab = get_pytorch_kobert_model()
```

드디어 BERT 모형을 불러왔다. bertmodel은 불러온 모델이 저장, vocab는 사용되는 한국어 단어가 저장 찾ㅇ자보니 vocab에는 8000여개의 한국어 단어가 들어가 있다고 한다. 근데 이거 매우 적은거다. 그래서 KoBERT의 한계점이라고도 불린다.

그리고 이때부터 살짝 브라우저의 반응이 늦게 온다. (시간이 걸린다...ㅠ)

```python
import pandas as pd
naturalTraining_data = pd.read_excel('.../감성대화말뭉치(최종데이터)_Training.xlsx')
```

기본적으로 데이터 프레임형식으로 불러온다.

이제 저 위의 데이터를 사용해보기 위해서는 데이터의 생김새를 확인해봐야한다. 그러기 위해서는 아래와 같은 코드로 일부분만 확인할 수 있다.

```python
naturalTraining_data.head()
naturalTraining_data.sample(n=10)
```

데이터를 전처리 한 후 모델 학습을 위한 세팅에 들어간다.

```python
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

KoBERT 모델에 들어갈 데이터 셋에 대한 class이다.

```python
# Setting parameters 필수
max_len = 64
batch_size = 64
warmup_ratio = 0.1
num_epochs = 15
max_grad_norm = 1
log_interval = 100
learning_rate =  5e-5
```

위의 parameter를 통해 인공지능을 학습시킵니다.

```python
#토큰화
tokenizer = get_tokenizer()
tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)

data_train = BERTDataset(dataset_train, 0, 1, tok, max_len, True, False)
data_test = BERTDataset(dataset_test, 0, 1, tok, max_len, True, False)

train_dataloader = torch.utils.data.DataLoader(data_train, batch_size=batch_size, num_workers=5)
test_dataloader = torch.utils.data.DataLoader(data_test, batch_size=batch_size, num_workers=5)
```

```python
class BERTClassifier(nn.Module): ## 클래스를 상속
    def __init__(self,
                 bert,
                 hidden_size = 768,
                 num_classes=6,   ##클래스 수 조정##
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

클래스 수 6개를 조정하고 이를 통해 인공지능의 black Box인 hidden layer까지의 세팅을 모두 갖춘다.

```python

#BERT 모델 불러오기
model = BERTClassifier(bertmodel,  dr_rate=0.5).to(device)
#optimizer와 schedule 설정
no_decay = ['bias', 'LayerNorm.weight']
optimizer_grouped_parameters = [
    {'params': [p for n, p in model.named_parameters() if not any(nd in n for nd in no_decay)], 'weight_decay': 0.01},
    {'params': [p for n, p in model.named_parameters() if any(nd in n for nd in no_decay)], 'weight_decay': 0.0}
]

optimizer = AdamW(optimizer_grouped_parameters, lr=learning_rate)
loss_fn = nn.CrossEntropyLoss()

t_total = len(train_dataloader) * num_epochs
warmup_step = int(t_total * warmup_ratio)

scheduler = get_cosine_schedule_with_warmup(optimizer, num_warmup_steps=warmup_step, num_training_steps=t_total)

#정확도 측정을 위한 함수 정의
def calc_accuracy(X,Y):
    max_vals, max_indices = torch.max(X, 1)
    train_acc = (max_indices == Y).sum().data.cpu().numpy()/max_indices.size()[0]
    return train_acc
    
train_dataloader

for e in range(num_epochs):
    train_acc = 0.0
    test_acc = 0.0
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

학습 진행

```python
## 학습 모델 저장
PATH = 'drive/MyDrive/colab/StoryFlower/bert' # google 드라이브 연동 해야함. 관련코드는 뺐음
torch.save(model, PATH + 'KoBERT_담화.pt')  # 전체 모델 저장
torch.save(model.state_dict(), PATH + 'model_state_dict.pt')  # 모델 객체의 state_dict 저장
torch.save({
    'model': model.state_dict(),
    'optimizer': optimizer.state_dict()
}, PATH + 'all.tar')  # 여러 가지 값 저장, 학습 중 진행 상황 저장을 위해 epoch, loss 값 등 일반 scalar값 저장 가능
```

모델을 저장한 이후 학습한 모델을 불러와 사용해야할 코드는 다음과 같다.

```python
!pip install mxnet
!pip install gluonnlp pandas tqdm
!pip install sentencepiece
!pip install transformers==3.0.2
!pip install torch

!pip install git+https://git@github.com/SKTBrain/KoBERT.git@master

# torch
import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np
from tqdm import tqdm, tqdm_notebook

#kobert
from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

#GPU 사용
device = torch.device("cuda:0")

#BERT 모델, Vocabulary 불러오기 필수
bertmodel, vocab = get_pytorch_kobert_model()


# KoBERT에 입력될 데이터셋 정리
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

# 모델 정의
class BERTClassifier(nn.Module): ## 클래스를 상속
    def __init__(self,
                 bert,
                 hidden_size = 768,
                 num_classes=6,   ##클래스 수 조정##
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

# Setting parameters
max_len = 64
batch_size = 32
warmup_ratio = 0.1
num_epochs = 20
max_grad_norm = 1
log_interval = 100
learning_rate =  5e-5

## 학습 모델 로드
PATH = 'drive/MyDrive/colab/StoryFlower/bert/'
model = torch.load(PATH + 'KoBERT_담화_86.pt')  # 전체 모델을 통째로 불러옴, 클래스 선언 필수
model.load_state_dict(torch.load(PATH + 'model_state_dict_86.pt'))  # state_dict를 불러 온 후, 모델에 저장

#토큰화
tokenizer = get_tokenizer()
tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)

def new_softmax(a) : 
    c = np.max(a) # 최댓값
    exp_a = np.exp(a-c) # 각각의 원소에 최댓값을 뺀 값에 exp를 취한다. (이를 통해 overflow 방지)
    sum_exp_a = np.sum(exp_a)
    y = (exp_a / sum_exp_a) * 100
    return np.round(y, 3)


# 예측 모델 설정
def predict(predict_sentence):

    data = [predict_sentence, '0']
    dataset_another = [data]

    another_test = BERTDataset(dataset_another, 0, 1, tok, max_len, True, False)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=5)
    
    model.eval()

    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)

        valid_length= valid_length
        label = label.long().to(device)

        out = model(token_ids, valid_length, segment_ids)

        test_eval=[]
        for i in out:
            logits=i
            logits = logits.detach().cpu().numpy()
            min_v = min(logits)
            total = 0
            probability = []
            logits = np.round(new_softmax(logits), 3).tolist()
            for logit in logits:
                print(logit)
                probability.append(np.round(logit, 3))

            if np.argmax(logits) == 0:  emotion = "기쁨"
            elif np.argmax(logits) == 1: emotion = "불안"
            elif np.argmax(logits) == 2: emotion = '당황'
            elif np.argmax(logits) == 3: emotion = '슬픔'
            elif np.argmax(logits) == 4: emotion = '분노'
            elif np.argmax(logits) == 5: emotion = '상처'

            probability.append(emotion)
            print(probability)
    return probability
```

이제 위 코드를 django 서버에 반영하면 된다.

특히 학습한 모델을 django에 로드할 때 필요한 클래스인 `BERTDataset`와 `BERTClassifier`은 manage.py에 세팅한 뒤에 모델을 Apps.py에서 불러오자.

## 📌 KNN 추천 알고리즘

- 유클리디안 거리 공식을 이용한 유사 감정 꽃 추천

- 매우 간단한 알고리즘이며, 코드 연산에 큰 어려움이 없기 때문에 자세한 부분은 생략한다.

  **Why KNN ?**

  - KoBERT 모델에 텍스트를 대입 후 나오는 결과물은 하나의 단어가 아닌 특정에 감정에 속할 가중치 값이다. 그 값이 클수록 해당 감정일 확률이 높음을 의미한다. 이는 본 프로젝트에서 주의해야할 부분이기도 하다. 한 사람이 입력한 텍스트 안에는 하나의 감정만 담겨있지 않다. 따라서 6개의 감정 대분류를 모두 검사하여 꽃말 + 꽃의 배경으로 인해 등장한 확률값과 사용자가 입력한 텍스트로 인해 등장한 확률값 사이의 가장 최소의 거리를 지닌 꽃을 추천하는 것이 모든 감정을 고려하여 사용자에게 꽃을 추천하는 맥락에 어울리기 때문에 실수를 치역으로 한 가중치 값을 softmax함수를 사용하여 텍스트를 확률값으로 변환했고 이를 통해 꽃을 추천하는 KNN 알고리즘을 사용하게 되었다.

``` python
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
```

django에 반영된 코드는 위와 같다.