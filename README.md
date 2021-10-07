
# 🌸 담화 (마음을 담은 이야기)

## 🖥 앱 소개
```
담화(이야기, 꽃)는 한국어를 사용하는 안드로이드폰, 아이폰 사용자간 입력한 
텍스트에 알맞는 꽃을 무료로 변환하여 전달하는 SNS 서비스입니다. 
특히 텍스트 감정 변환의 경우 KoBERT 모델을 활용하여 6개의 감정으로 대분류합니다.
그리고 이와 알맞는 감정을 지닌 꽃말을 꽃을 추천 받을 수 있습니다.
또한 어플리케이션의 UI와 알맞은 수채화 화풍은 딱딱한
현대 이미지를 탈피하여 부드럽게 접근할 수 있도록 돕고 있습니다.

꽃 선물은 특별한 날에만 하는 것이 아닙니다.
출근, 등교, 퇴근, 하교, 잠깐의 쉬는 시간이라는 사소한 시기에도
상대방 또는 자신에게 간단하게 마음을 전달할 수 있는 방법은
나만의 글에서 나오는 감정에 알맞는 꽃을 전달하는 것 일 것입니다.

저희 담화(이야기, 꽃)는 이부분에 집중했습니다.
아무리 사소한 시간과 장소일지라도 꽃이라는 부담스러움을
SNS 서비스로 승화시켜 간단히 마음을 전달할 수 있도록 사용해보세요.

대한민국 대부분의 국민이 가입되어 있는 카카오톡을 이용하여 손쉽게 가입할 수 있으며
텍스트를 입력하여 상황에 알맞는 꽃을 추천받아 동료, 가족, 친구, 자신에게 선물할 수 있습니다.

담화(이야기, 꽃)와 함께 사랑하고 아끼고 위로를 주고 싶은 사람에게 수채화로 그려진 꽃을 추천받아 전달해보세요.
그리고 달력으로 관리해보세요.
```
![](https://i.imgur.com/TI6HvVB.png) ![](https://i.imgur.com/X6fvadR.png)

![](https://i.imgur.com/pa4bJeR.png) ![](https://i.imgur.com/Z8VSWys.png)

**주요 기능:**
- 🌸 텍스트 입력에 따른 알맞는 꽃말과 꽃을 상대방과 자신에게 추천
- 🌷 카카오톡 로그인
- 🌼 카카오톡 공유하기
- 🌺 달력을 통한 전달 및 추천 받은 꽃 기록 관리

### 👨‍👨‍👧‍👦 팀원 소개


|          **[김정윤](https://github.com/kjungyoun)**          |          **[박민광](https://github.com/minkpang)**           |          **[박준규](https://github.com/Devjunku/)**          |           **[유희진](https://github.com/Huijiny)**           |          **[전중훈](https://github.com/uj02017/)**           |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="http://k.kakaocdn.net/dn/bceu1q/btrd1O7abnj/1BF1LZukkNMq6a39q6y4N1/img_640x640.jpg" width=180px, height=180px> | <img src="https://avatars.githubusercontent.com/u/50613287?v=4"  width=180px, height=180px> | <img src="https://avatars.githubusercontent.com/u/77471689?v=4"   width=180px, height=180px> | <img src="https://avatars.githubusercontent.com/u/37477660?v=4"  width=180px, height=180px> | <img src="https://images-ext-2.discordapp.net/external/dy3XGvcdHi77QPJuEh_UN8QXqSlsgR0LJknfmmXP_C0/https/ifh.cc/g/FSbJ3q.jpg?width=515&height=686"  width=180px, height=180px> |
|                   BackEnd(Spring, Django)                    |                        FrontEnd(IOS)                         |                팀장 & FrontEnd(Web, Android)                 |                    FrontEnd(Web,Android)                     |                   BackEnd(Spring, Django)                    |
| RestAPI <br /> 서버 배포 <br /> 화풍변환  </br> DB  </br> ERD  </br> 추천 알고리즘 </br> Bert |                 IOS 총괄 </br> 와이어 프레임                 | BERT 학습  </br> 와이어 프레임  </br> WebView  </br> Android |        Android 총괄 </br> 와이어프레임  </br> Webview        | RestAPI <br /> 서버 배포<br /> 화풍변환  </br> DB  </br> ERD  </br> 추천 알고리즘 </br> Bert |



## 📆 개발 기간

- 1~3주차
    - 아이디어 & 컨셉 회의
    - 기능 회의
    - 요구사항 명세서 작성
    - UI 설계
    - 데이터베이스 설계
    - 프로젝트 아키텍쳐 설계

- 4~6 주차
    - 기능 구현 (Android, IOS, Server)
        - Android
        - IOS
        - WebView
        - Server

- 7주차
    - Debug & Test
    - AWS EC2 서버 배포
    - AWS S3 서버 배포
    - WebView 배포
    - Android, IOS 앱 심사 진행
    - UCC 제작
    - 프로젝트 명세서 작성
    - 발표 준비

## 🛠 기술 스택
-  FrontEnd Stack
    - Android
        - Foundation 
          - AppCompat
          - Android KTX
          - Jetpack Navigation
          - Fragment
          - ViewPager2
        - Architecture
          - Data Binding
          - Lifecycles
          - ViewModel
        - Third party and miscellaneous libraries
          - Glide
          - Retrofit
          - OkHttp
          - Firebase
          - RxJava, RxAndroid, RxKotlin
          - CarouselRecyclerView
          - Kakao SDK
          - Lottie
    - IOS
        - SwiftUI
        - Alamofire
        - KakaoSDK
    - WebView
        - Vue2, 3
- BackEnd Stack
    - KNN
    - KoBERT
    - RestTemplate
    - Spring Boot
    - JPA
    - Django
    - MySQL
    - Docker
    - AWS EC2
    - AWS S3

### 🪜 Architecture

- 앱의 경우 MVVM 및 Repository 패턴을 사용하였습니다.
- 또한, DI 라이브러리를 사용하지 않았기 때문에 의존성의 경우 따로 DamhwaInjection 오브젝트를 만들어 주입해주는 형식으로 사용했습니다.
- 서버의 경우 Spring, Django 서버를 만들어 RestTemplate을 통해 HTTP 통신을 하도록 구현하였습니다.

![KakaoTalk_Photo_2021-10-08-00-13-23](https://user-images.githubusercontent.com/48318620/136413299-996c3415-922b-46ad-aa5d-c2462464daaf.png)

## 📑 추천 알고리즘 구현 
- 🤖 KoBERT & BERT에 대한 기본적인 설명
    - 자연어 처리 분야에서 2019년에 한 학술지에 엄청난 성능을 지닌 모델이 나왔으며. 그게 바로 BERT이다.
    - BERT는 Bidirectional Encoder Representations from Transformer의 약자로 텍스트를 양방향(앞뒤)로 확인하여 자연어를 처리하는 모델이다. 기존의 자연어 처리 모델은 단방향 우리가 글을 읽는 순서인 왼쪽→오른쪽으로 갔지만 BERT는 이 순서를 양방향으로 보기 때문에 다른 모델에 비해 매우 높은 정확도를 나타낸다. 그리고 무엇보다 대형 인공지능의 일종(구글에서 개발)이고 오픈소스이기 때문에 누구나 사용할 수 있는 장점이 있다.
    - 본 특화 프로젝트에서는 인공지능 모델 구축에서 Colab을 활용했고 GPU를 통해 모델 학습에 걸린 시간은 2시간이며 하나의 모델을 구축하는데 많은 시간이 걸린다. 따라서 Colab 환경에서 지원하는 GPU 또는 TPU를 사용하자. CPU로 연산을 수행하면 아에 진행되지 않음을 느낀다.
    - 그리고 KoBERT라는 것은 한국어의 경우 다른 나라의 언어보다 훨씬 복잡해서 SKT-brain에서 BERT의 한국어 버전을 만들었다. 예측률이 훨씬 좋다고한다. 특히 관련 이슈를 [https://github.com/SKTBrain/KoBERT/pulls?q=is%3Apr+is%3Aclosed](https://github.com/SKTBrain/KoBERT/pulls?q=is%3Apr+is%3Aclosed) 에서 검색하여 해결할 수 있으니 참고하면 된다.
    - BERT를 학습하기 위해서 사용한 라이브러리는 `kobert, pytorch`이다.
    
- KNN 알고리즘 구현
    - 가장 기본적인 예측 분류 모형으로 나쁘지 않은 성능을 지니고 있다.
    - 담화의 추천은 꽃으로 이뤄진다. 꽃의 수가 많아졌을 때 KNN이 아닌 다른 모형을 고려해볼만 하다.
