# 📑 담화 BackEnd 진행 상황

- 참여자
    - 김정윤
    - 전중훈

## 📝 Django Project Setting 실습

### 📌 Django 참고 Github

[GitHub - djangobusan/recommender-system-hands-on-part1](https://github.com/djangobusan/recommender-system-hands-on-part1)

[GitHub - dowookims/HONEYBEE: 🎞movieLens 1m 데이터를 활용한 개인 영화 추천 사이트🎞](https://github.com/dowookims/HONEYBEE)

### 🧾 Django 실습

- Django 프로젝트 생성 명령어
- Django 프로젝트 생성하기 전에 반드시 가상환경인지를 확인한다!!!

    > Django는 가상환경 상에서 돌려야 하므로 `pip list` 를 통해 나오는 라이브러리가 많을 경우 전역 환경, 적을 경우 가상 환경이라 판단

    - Django 가상환경 돌리기

    [맥북(Mac OS) 아나콘다 및 파이썬 설치하는 방법](https://travislife.tistory.com/26)

    [[Python] venv로 가상환경 관리하기](https://hyun0k.tistory.com/entry/Python-venv%EB%A1%9C-%EA%B0%80%EC%83%81%ED%99%98%EA%B2%BD-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0)

    ```bash
    # 장고 프로젝트 생성
    mkdir djangoPrj01

    # 가상환경 생성

    # anaconda 로 만들기
    conda create -n (name) python=3.8

    # python으로 만들기
    python -m venv 가상환경이름

    # 활성화

    # anaconda 버전
    conda activate (name)

    # python 버전
    source venv01/bin/activate

    # 비활성화

    # anaconda 버전
    conda deactivate

    # python 버전
    deactivate

    # 가상환경 목록 보기
    conda info --envs

    # 가상환경 삭제

    # conda 버전
    conda env remove --n "가상환경명"

    # python 버전
    rm -rf <가상환경 이름>
    ```

- 직접 pycharm에 들어가서 생성해도 된다.

    → pycharm에 들어가서 생성하면 자동 가상환경으로 생성해줌

```bash
django-admin startproject "프로젝트이름"
```

### 📌 Django Rest Framework 설치

- Django REST framework 공식문서

[Home - Django REST framework](https://www.django-rest-framework.org/#example)

- Django 프로젝트 상에서 REST API 구현을 위함

```bash
pip install djangorestframework
```

→ 설치한 다음 `pip list` 를 통해 제대로 설치되었는지 확인해본다.

- 설치 후 settings.py 에 rest_framework 추가
- INSTALLED_APPS 에 추가할 때는 제일 위에 추가해주는 것이 좋다.

![8](https://user-images.githubusercontent.com/48318620/133715453-053d8a60-e2e4-4a9e-b5c5-f47284735b8d.png)

- 추가로 settings에 아래 코드를 추가해준다.

```python
REST_FRAMEWORK = {
    # Use Django's standard `django.contrib.auth` permissions,
    # or allow read-only access for unauthenticated users.
    'DEFAULT_PERMISSION_CLASSES': [
        'rest_framework.permissions.DjangoModelPermissionsOrAnonReadOnly'
    ]
}
```

### 📑 CORS 설정

여러 호스트에서 접근할 경우 CORS 오류가 나기 때문에 이를 방지하기 위한 설정이다.

[Django CORS 설정하기](https://hyeonyeee.tistory.com/65)

- django-cors-headers 설치한다.

```bash
pip install django-cors-headers
```

- settings.py에 설정을 추가해준다.
- INSTALLED_APPS, MIDDLEWARE, 관련 설정 추가

```python
INSTALLED_APPS =[ ... 'corsheaders', # CORS 관련 추가 ]

... 

MIDDLEWARE = [ 'corsheaders.middleware.CorsMiddleware', # CORS 관련 추가 ... ]

... 

# CORS 관련 추가 
CORS_ORIGIN_WHITELIST = ['http://127.0.0.1:3000' 
												,'http://localhost:3000']
CORS_ALLOW_CREDENTIALS = True

```

### 📌 requirements.txt 에 python 모듈 정보 저장

```bash
# 저장 명령어
pip freeze > requirements.txt

# 설치 명령어
pip install -r requirements.txt
```

---

### 📌 Mac MySQL 환경 변수 설정

[[MySQL] MacOS 에서 MySQL 설치](https://ssungkang.tistory.com/entry/MySQL-MacOS-%EC%97%90%EC%84%9C-MySQL-%EC%84%A4%EC%B9%98)

```bash
export DB_HOME=/usr/local/mysql
export PATH="$PATH:/usr/local/mysql/bin"
```

## MSA 자료 조사

## 📑 MSA / Spring Cloud

[(Spring Boot / Spring Cloud / MSA) 1. 왜 MSA를 선택하였을까?](https://lion-king.tistory.com/10)

### ✏️ Monolithic Architecture

- 하나의 Database에 3개의 모듈에서 사용하는 모든 테이블이 존재
- 사각형이 가로로 묶임

![Untitled](https://user-images.githubusercontent.com/48318620/133714496-b8f1ee3c-d785-4331-a560-124aa389519a.png)

### ✏️ Micro Service Architecture (MSA)

- 각각의 마이크로 서비스들은 서비스마다 독립적으로 자신의 Presentation Layer, Business Layer, Database를 가짐
- 이러한 구분으로 하나의 서비스 단위와 하나의 마이크로서비스가 일치
- 사각형이 세로로 묶임
- 장점
    1. 시스템적인 측면에서 각 마이크로서비스들은 독립적이므로 해당 서비스에 가장 적합한 언어, 버전을 선택할 수 있고 데이터베이스도 제약없이 선택 가능
    2. 하나의 마이크로서비스에서 변경이 발생하여도 다른 서비스에 영향을 미치지 않고 동작이 멈추더라도 다른 서비스들은 동작을 지속할 수 있다.
    3. 개발적인 측면에서는 서비스 단위로 나뉘어진 개발팀이 서로 다른 기본 기술과 배포 일정을 선택할 수 있게 되고, 특정 서비스에만 집중하기 때문에 코드에 대한 이해도가 높다.
- Monolithic Architecture 구조는 프로젝트의 크기가 커질수록 빌드 시간도 오래걸리고 간단 버그나 수정사항이 있을 때 전체를 다시 배포해야하는 번거러움, 작은 오류가 전체 시스템에 영향을 주는 상황들이 있음

![Untitled1](https://user-images.githubusercontent.com/48318620/133714499-b897079b-5178-45bc-a07a-80730b7328d7.png)

### 📌 Spring Cloud - MSA를 선택했다면 Spring Cloud를 적용하자.

- Spring Cloud는 분산 시스템에서 공통적인 패턴(구성 관리, 서비스 검색, 지능형 라우팅 등)을 모아 신속하게 구축할 수 있는 도구를 스프링 라이브러리 형태로 제공
- 분산 시스템에서 필요한 부분들에 대한 부담을 덜고 충실하게 서비스의 기능을 구현하는 것에 충실할 수 있음
- Spring Cloud와 함께 Spring Config, RabbitMQ, Netflix의 오픈소스인 Eureka, Zuul을 활용하여 이상적인 마이크로서비스의 개발을 도울 수 있음

![2](https://user-images.githubusercontent.com/48318620/133714488-f914e28a-3c62-49b5-9737-2a163cf4aca6.png)

- **Spring Config** : Spring Boot Application은 application.properties 혹은 application.yml 파일에 환경설정을 저장하고 이 파일의 정보를 읽어 빌드하는데, 이 파일들은 해당 프로젝트와 함께 저장된다.

    → Spring Cloud Config 서버를 두어 사용하면 모든 Spring Boot Application의 환경설정 파일을 한 곳에 저장시킬 수 있고 해당 서버에 접근하여 환경설정 정보를 가져오도록 할 수 있다.

    → 이렇게 적용하면 모든 Application의 환경설정 정보를 한곳에서 관리가 가능하고 환경설정이 바뀌어도 Application 전체를 다시 빌드하지 않아도 된다.

    → 환경 설정이 바뀌면 해당 서버에 반영해야하고 확인도 해당 서버에서 해야하는 불편한 점 존재

- **RabbitMQ** : AMQP (Advanced Message Queueing Protocol) 로 만들어져 있으며 Message Queue를 제공한다.

    → 이상적인 마이크로서비스 환경은 마이크로서비스 사이의 통신이 비동기적으로 이루어지는 것인데, RabbitMQ를 사용하면 마이크로 서비스들이 외부의 Queue를 통해 메세지를 주고받도록 함으로써 쉽게 이 부분을 구성할 수 있다.

- **Eureka** : 마이크로서비스들의 정보를 레지스트리에 등록할 수 있도록 하고 마이크로서비스의 동적인 탐색과 로드밸런싱을 제공한다.

    ![3](https://user-images.githubusercontent.com/48318620/133714492-b4f9dfcb-085c-40b9-bbeb-738186bf29ef.png)

    유레카 서버 설정 파일인 'application.yml' 파일의 설정값이다.

    유레카 서버는 마이크로서비스가 자신의 정보를 유레카 서버에 등록할 수 있게 서버 주소를 알려주어야 한다.

    마이크로서비스는 기동 시 자신의 상태 정보를 'defaultZone'에 선언된 유레카 서버에게 알려준다.

    유레카 서버의 주소 정보는 마이크로서비스의 설정에도 포함되어 있어야 한다.

    설정값 중 'default zone: http://localhost:9091/eureka/’ 부분이 마이크로서비스가 참조하는 유레카 서버 주소이다.

    유레카 서버 애플리케이션에서 ‘@EnableEurekaServer 어노테이션을 등록하는 것 만으로 간단하게 유례카 서버를 동작시킬 수 있다.

    유레카 서버는 클라이언트들의 등록 상태를 확인 할 수 있는 별도의 웹 화면을 제공한다.

    마이크로서비스에서 '@EnableDiscoveryClient' 어노테이션 설정과 몇 가지 설정을 추가하고 기동하면 유레카 서버에 등록되고 웹 화면에서 확인할 수 있다.

- **Zuul** : 모든 마이크로서비스에 대한 요청을 먼저 받아들이고 라우팅하는 프록시 API Gateway 기능 및 부하분산 설정을 수행한다.

    → 부하 분산은 동일한 서비스가 여러 서버에 배포되어 있을 때 부하를 분산시켜 주는 기능

    → 서비스 라우팅은 zuul 서버에서 설정한 context path를 기준으로 마이크로서비스를 라우팅 해주는 역할

    → Zuul 서버도 설정 서버와 서비스 등록 감지 서버를 사용하기 위해 application.yml 파일에 사용 설정을 한다.

    ![4](https://user-images.githubusercontent.com/48318620/133714493-a128dbad-5731-4511-9717-5cd3807c5fc7.png)

    줄 서버에서 라우팅 대상 서비스를 찾아가기 위해 콘텍스트 패스를 지정한다. 그리고 지정된 콘텍스트 패스에는 마이크로서비스의 이름이 매핑되어 있다.

    마이크로 서비스의 이름은 유레카 서버에 등록되어 있고, 줄 서버는 유레카 서버에 등록된 마이크로서비스의 이름을 참조한다.

    ⭐️ 외부로 노출된 줄 서버의 REST URL은 ‘http:// Iocalhost:9090/’이고, 만약 콘텍스트 경로를'/coffeeOrder'로 요청하면 줄 서버 설정 값인 'path' 중 '/coffeeOrder'에 매핑되어 있는 마이크로서비스인 'msa-service-coffee-order' 즉, ‘커피 주문 마이크로서비스’로 연결된다.

    각 마이크로서비스의 물리적인 주소와 매핑되는 이름(spring.application.name)이 이미 유레카 서버에 등록되어 있어 줄 서버에 라우팅 정보를 설정할 때는 마이크로서비스의 이름을 이용하여 설정할 수 있다.

    좀 더 구체적으로 설명하면 '커피 주문', '커피 회원'， '주문 처리 상태' 마이크로서비스는 각각 고유의 이름을 가진다.

    이 이름은 각 서비스의 'application.yml'에서 정의하였고, 서비스가 기동될 때 유레카 서버에 등록된다.

    줄 서버는 등록된 서비스의 이름을 이용하여 서비스 라우팅을 수행한다.

    줄 서버는 마이크로서비스의 'serviceld'를 이용하여 마이크로서비스로 클라이언트 요청을 라우팅한다.

    줄 서버에서 참조하는 'serviceld'는 마이크로서비스의 'application.yml'에서 지정한 'spring.application.name'이다. 

    마이크로서비스가 기동될 때 마이크로서비스의 애플리케이션 이름이 유레카 서버에 등록되고, 각 마이크로서비스에 설치된 유레카 클라이언트를 사용해서 유레카 서버에 등록된 각 마이크로서비스의 애플리케이션 이름을 참조할 수 있다.

    줄 서버도 유레카 서버에 등록된 애플리케이션 이름을 참조하여 각 서비스로 라우팅하기 위한 Context path를 다르게 설정합니다. Context path 란，애플리케이션을 구분하기 위한 경로이다.

    줄 서버 애플리케이션에서 '@EnableZuulProxy' 어노테이션을 등록하는 것만으로 간단 하게 줄 서버를 동작시킬 수 있다.

- 참고

[스프링 클라우드로 MSA 구축하기](https://chronic794.blogspot.com/2021/02/msa_22.html)

### 💯 추후 계획

```
조사 결과 Eureka Server & Zuul Server를 이용하여 구축하는 방식은 대규모 프로젝트에
더 잘 어울리는 방식으로 생각되고 구현이 복잡함 (많은 서버를 구축해두어야 한다.)
따라서, Eureka Server & Zuul Server를 이용하여 구축하는 방식이 아닌 각 서버간 http 통신을
이용하여 Spring RestTemplate을 이용한 통신으로 구현 예정.
```

## 📖 DataBase 설계

### 📑 테이블 설계

### 📌 user table (사용자 테이블)

```
- userno (사용자 번호) → PK (long)
- email (사용자 이메일) → unique (String)
- username (사용자 이름) → String
- profile (사용자 프로필 사진) → String
- token (사용자 인증 토큰) → String
```

### 📌 flower table (꽃 정보 테이블)

- 이미지를 수채화로 바꾸는 방법


```
- fno (꽃 번호) → primary key (long)
- fnameKR (꽃 이름 한글) → String
- fnameEN (꽃 이름 영어) → String
- fmonth (월) → int (탄생화 구현 여부?) ⇒ 자율 프로젝트를 위함 (추가 기능)
- fday (일) → int (탄생화 구현 여부?) ⇒ 자율 프로젝트를 위함 (추가 기능)
- flang (꽃말) → String
- fcontents (꽃 내용) → String
- fuse (꽃 이용 방법) → String
- fgrow (꽃 기르는 방법) → String
- flocation (자생지) → String
- img1 (이미지 주소1) → String
- img2 (이미지 주소2) → String
- img3 (이미지 주소3) → String
```

### 📌 ⭐️ feeling table → 인공지능에 필요한 DB  수정 필요

```
- feelingno (기분 번호)
- feelingname (기분명) → ex) 맑음, 조금 우울, 행복, 등등
- positive (긍정 비율) → 60
- negative (부정 비율) → 40
```

### 📌 history (이전 추천 결과, 메세지 내용 저장 테이블)

- 서신 쓰기 메인 페이지에서 서신 메세지 입력할 때 '누구에게' 보내는지 입력할 수 있게끔 하면 누구에게 보낸 메세지인지를 history에서 보여줄 수 있을 듯 → 메모 느낌
- 히스토리에 서신 추천 결과는 한 개만 저장

```
- hno (히스토리 번호) → PK (long)
- userno (사용자 번호) → FK [User<userno>]
- fno (추천 결과 꽃 번호) → FK [Flower<fno>]
- htype {서신(1) or 기분(0)} → tiny int (boolean) 1,0
- contents (메세지 내용) → String
- regdate (등록 날짜) → date
- to (상대방 정보) → String
```
### 설계 산출물

- 구글 스프레드 시트 (상세 설계 -> 추후 수정 필요)

![5](https://user-images.githubusercontent.com/48318620/133714911-a6e38277-b44f-4e16-b9dc-eb8368448c05.png)

- ERD_Cloud

![6](https://user-images.githubusercontent.com/48318620/133714921-c1b865bf-4f1f-41c3-a1fd-1978c70b00ac.png)

- MySQL ER-Diagram

![7](https://user-images.githubusercontent.com/48318620/133714925-ca62cb29-268b-4716-a3da-d303a62e3e3e.png)

- MySQL Code
    첨부 파일로 업로드
