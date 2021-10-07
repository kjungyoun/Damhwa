# 🧾 담화 Server Docs

### Teck Stack & IDE
- Intellij-2020.02
- Pycharm-2021.02
- Spring Boot
- JPA
- Django
- MySQL
- Docker
- AWS EC2
- AWS S3

### 👨‍👧‍👦 진행 인원
- 김정윤
- 전중훈

## 📆 개발 일정

### 1~3주차
- 아이디어 & 컨셉 회의
- 기능 회의
- 요구사항 명세서 작성
- UI 설계
- 데이터베이스 설계
- 프로젝트 아키텍쳐 설계

### 4~6 주차
- 기능 구현
- KoBERT 모델 구현
- KNN 알고리즘 구현

### 7주차
- Debug & Test
- AWS EC2 서버 배포
- UCC 제작
- 프로젝트 명세서 작성
- 발표 준비

## 📝 요구사항 명세서

![스크린샷 2021-10-07 오후 6 34 07](https://user-images.githubusercontent.com/48318620/136359478-2ec00a69-7a83-44ac-9780-037cf5caa229.png)

## 📌 Feature

|분류|구현기능|세부|작성여부(O/X)|
|:---:|---|---|:---:|
|Main|랜딩페이지 Controller||O|
|Main|카카오 로그인||O|
|이야기|서신분석|서신 텍스트 분석|O|
|이야기|서신분석|분석 결과 조회|O|
|이야기|서신분석|서신 저장|O|
|기분|기분분석|기분 텍스트 분석|O|
|기분|기분분석|기분 저장|O|
|꽃 달력|서신관리|캘린더 등록|O|
|꽃 달력|서신관리|리스트 조회|O|
|꽃 달력|서신관리|이야기 조회|O|
|꽃 달력|서신관리|기분 조회|O|

## 📂 사용한 공공 데이터

[농촌진흥청 국립원예특작과학원_오늘의 꽃 조회 서비스(2.0)](https://www.data.go.kr/data/15084605/openapi.do)

- 공공데이터를 csv 파일로 변환 후 DB에 저장

## 🖌 이미지 수채화 필터링

[tf.keras를 사용한 Neural Style Transfer | TensorFlow Core](https://www.tensorflow.org/tutorials/generative/style_transfer?hl=ko)

[Deep Dream Generator](https://deepdreamgenerator.com)

- 공공데이터에 저장된 이미지를 수채화 필터링 적용 후 AWS S3에 업로드 후 사용

## 📖 Architecture
- Spring과 Django 두 가지 서버를 구현하여 RestTemplate을 이용한 서버 간 HTTP 통신 구축
- Spring 서버 포트 : 8080
- Django 서버 포트 : 8000
- Front-End에서의 기본적인 요청은 Spring에서 받아 응답
- 사용자 메세지를 입력받아 Spring으로 요청이 들어오면 Spring에서 Django 서버 호출
- Django는 요청으로 들어온 메세지를 KoBERT Model 연산, KNN 추천 알고리즘 계산을 수행하여 응답
- REST API

![스크린샷 2021-10-07 오후 9 02 02](https://user-images.githubusercontent.com/48318620/136380341-912a329b-1de0-47c1-8eed-515c29106978.png)

## ⚙️ AWS EC2 배포
- Docker Hub를 이용하여 프로젝트 Build 후 생성된 이미지를 Docker Hub에 push
- EC2 서버에서 push 된 이미지를 pull 하는 방식으로 배포
- Front의 Vue는 빌드파일인 dist파일을 SFTP를 이용하여 EC2 서버로 전송 후 NGINX를 이용하여 배포


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

# Container 별칭 사용
docker run -d -p [호스트포트]:[컨테이너포트] --name [사용할 컨테이너별칭][생성한 이미지 이름] [생성한 이미지 이름]

# 예시 (앞포트 = 도커 포트 / 뒷포트 = 이미지 포트)
$ sudo docker run -d -p 8080:8080 youn0729/damhwa:latest
```

## 💿 데이터 모델 불러오기

### 가상환경에 KoBert 설치

[GitHub - SKTBrain/KoBERT: Korean BERT pre-trained cased (KoBERT)](https://github.com/SKTBrain/KoBERT)

※ python 버전은 3.7, transformers 버전은 3.0.2로 맞춰줘야함 ※

