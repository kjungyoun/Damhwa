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

## 📌 KNN 추천 알고리즘

- 유클리디안 거리 공식을 이용한 유사 감정 꽃 추천