# ๐ธ ๋ดํ Frontend 

> ํฅ๊ธฐ๋กญ๊ฒ ๋ง์์ ์ ํ๋ ๋ฐฉ๋ฒ, ๋ดํ ์๋๋ค.

ํ๋ก ํธ์๋ ๊ฐ๋ฐ์ Android์ iOS ๋ ํ๋ซํผ์ ํตํด ๊ฐ๋ฐํ์ผ๋ฉฐ, ๋ด๋ถ์ Webview๋ฅผ ์ฌ์ฉํ์ฌ ํ์ด๋ธ๋ฆฌํธ ํ์์ผ๋ก ๋ง๋ค์์ต๋๋ค. ์๋ ์ ํํ ํํฌ์คํ์ ์ ์ด๋๊ฒ ์ต๋๋ค.



## Android

### โ๏ธAndroid Studio IDE Setup

For development, the latest version of Android Studio is required. 



### ๐  Teck Stack

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

### MAD Scorecard

Modern Android Development(MAD)๋ ๋ ๋์ ์ ํ๋ฆฌ์ผ์ด์์ ๊ตฌ์ถํ๋๋ฐ ๋์์ ์ฃผ๋ Blueprint์๋๋ค. ํ์ฌ ์ ํฌ ํ์ Android ํ๋ก์ ํธ๊ฐ ์ผ๋ง๋ ์ต์  Android ํ๋ซํผ, ๋ผ์ด๋ธ๋ฌ๋ฆฌ, ๊ธฐ์ ์ ์ฌ์ฉํ๋์ง ํ์ธํฉ๋๋ค.

![jetpack](README.assets/summary.png)

![jetpack](README.assets/jetpack-3600462.png)



### ๐ช Architecture

์ํคํ์ฒ์ ๊ฒฝ์ฐ MVVM ๋ฐ Repository ํจํด์ ์ฌ์ฉํ์์ต๋๋ค.

๋ํ, DI ๋ผ์ด๋ธ๋ฌ๋ฆฌ๋ฅผ ์ฌ์ฉํ์ง ์์๊ธฐ ๋๋ฌธ์ ์์กด์ฑ์ ๊ฒฝ์ฐ ๋ฐ๋ก DamhwaInjection ์ค๋ธ์ ํธ๋ฅผ ๋ง๋ค์ด ์ฃผ์ํด์ฃผ๋ ํ์์ผ๋ก ์ฌ์ฉํ์ต๋๋ค.

![image](https://user-images.githubusercontent.com/22849063/132246469-3bcc36b3-70f3-4ee2-b32d-851bd77dcadd.png)

### ๐ท ScreenShots

#### 1. ์์ ์ฐ๊ธฐ > ์ถ์ฒ

<img src="README.assets/image-20211007183807733.png" alt="image-20211007183807733" style="zoom:50%;" /><img src="README.assets/image-20211007183816052.png" alt="image-20211007183816052" style="zoom:50%;" /><img src="README.assets/image-20211007183826115.png" alt="image-20211007183826115" style="zoom:50%;" />



#### 2. ๊ธฐ๋ถ์ฐ๊ธฐ > ์ถ์ฒ

<img src="README.assets/image-20211007183947746.png" alt="image-20211007183947746" style="zoom:50%;" /><img src="README.assets/image-20211007183921086.png" alt="image-20211007183921086" style="zoom:50%;" />



#### 3. ๊ฝ ๋ฌ๋ ฅ -> ์์ธ๋ณด๊ธฐ (Vue.js)

<img src="README.assets/image-20211007184004338.png" alt="image-20211007184004338" style="zoom:50%;" />

## iOS

### โSwift5 with Xcode12


### ๐  Teck Stack

- Swift Packeges
  - ACarousel
  - Lottie
- Firebase 
- KakaoSDK
- Alamofire

### ๊ตฌํ ๋ด์ฉ

- SwiftUI๋ฅผ ํตํ UI ๊ตฌํ์ ์งํํ์์ต๋๋ค.
- ์์ ๋ก๊ทธ์ธ ๊ธฐ๋ฅ๊ตฌํ (Kakao, Apple), ๋ก๊ทธ์ธ์์ด ์ฌ์ฉ๊ฐ๋ฅ (๋ฐฐํฌ ์ฌ์ฌ) 
- WebView ์ฐ๋ ๋ฐ ๋ค์ดํฐ๋ธ์์ JS ํจ์ ํธ์ถ
- API ํต์ ์ ์ํ์ฌ Alamofire ์ฌ์ฉ
- KakaoSDK ์ฌ์ฉํ์ฌ ๋ฉ์ธ์ง ๊ณต์ ๊ธฐ๋ฅ ๊ตฌํ



## Web

์น์ ๊ฒฝ์ฐ ์น๋ทฐ๋ฅผ ๊ตฌํํ๊ธฐ ์ํด ์ฌ์ฉํ์์ต๋๋ค.

Calendar์, ์์ ๋ณด๋ ํ์ด์ง ๋ฐ ๊ฐ์ ๊ธฐ๋ก ๋ณด๋ ํ์ด์ง ์ด 3๊ฐ์ ํ์ด์ง๋ก ๊ตฌ์ฑ๋์ด์์ต๋๋ค.

### โ๏ธVisual Studio Code IDE Setup

For development, the latest version of Visual Studio Code is required. 



### ๐  Teck Stack

- Vue.js 
- Vue-router 
- Boostrap5 



### ๐ท ScreenShots



