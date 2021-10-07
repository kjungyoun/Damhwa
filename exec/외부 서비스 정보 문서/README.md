# 📄 프로젝트에서 사용하는 외부서비스 정보 문서

사용한 정보 문서: 카카오 소셜 로그인, 카카오톡 공유, Apple Login

### 📌 카카오 소셜 로그인

- 안드로이드 Kotlin 및 ios Swift 에서 카카오 소셜 로그인 구현
- 소셜 로그인을 구현하기 위해 카카오 개발자 페이지에서 앱 등록 및 API key값 생성
- 카카오에 로그인 후 AccessToken으로 카카오에 등록된 회원의 정보를 받아와 DB에 저장

##### 🌸 안드로이드 카카오 톡 로그인 구현 방법

1. `kakao developer접속` → `login` → `my application` → `서비스 플랫폼 url 및 package 등록`

2. 안드로이드 스튜디오 build.gradle(module) → 필요 `dependencies` 아래와 같이 추가

   ```kotlin
   // 소셜 로그인 카카오
   def kakaoLoginVersion = "2.8.1"
   implementation "com.kakao.sdk:v2-user:$kakaoLoginVersion" // 카카오 로그인
   implementation "com.kakao.sdk:v2-user-rx:$kakaoLoginVersion"
   implementation "com.kakao.sdk:v2-link:$kakaoLoginVersion" // 카카오 링크 / 아래 카카오 링크를 위한 디펜던시
   implementation "com.kakao.sdk:v2-link-rx:$kakaoLoginVersion"
   ```

3. 카카오 플랫폼 키 전역 변수 설정

   ```kotlin
   package com.example.damhwa_android.constants
   
   object Constants {
       const val KAKAO_KEY = "받은 카카오키 등록"
   }
   ```

4. 카카오 SDK 설정 damhwa_android 패키지 내에 `Globalapplication.kt` 파일 생성 및 코드 작성

   ```kotlin
   import android.app.Application
   import com.example.damhwa_android.constants.Constants.KAKAO_KEY // 3번의 kakao key를 여기서 이용
   import com.example.damhwa_android.data.sharedpreferences.DamhwaSharedPreferencesImpl
   import com.kakao.sdk.common.KakaoSdk
   
   
   class GlobalApplication : Application() {
       override fun onCreate() {
           super.onCreate()
           initializeKakaoSDK()
           initializeSharedPreferences()
       }
   
       private fun initializeKakaoSDK() {
           KakaoSdk.init(this, KAKAO_KEY)
       }
   
       private fun initializeSharedPreferences() {
           DamhwaSharedPreferencesImpl.init(this)
       }
   }
   ```

5. `AndroidManifest.xml`파일에서 android:name=".GlobalApplication" 설정

   ```xml
   <application
                ...
           android:name=".GlobalApplication"
                ...
   </application>
   ```

6. 카카오 키값을 xml에서 활용하기 위해서 values의 strings.xml에서 이를 추가

   ```xml
   <string name="kakao_app_key">여기에 카카오키를 입력해주세요</string> 
   ```

   이제 위의 `kakao_app_key`를 `xml`에서 사용할 것임

7. 이후 다시 `AndroidManifest.xml`파일에서 kakao_app_key를 사용

   ```xml
   <activity
   	...
   
       <data
        android:host="oauth"
        android:scheme="@string/kakao_app_key" />
   	...
   </activity>
   ```

8. 이후 아래와 같이 로그인이 일어나는 activity.kt파일에서 카카오 사용자 요청을 보내면 된다.

   ```kotlin
   // fragment에서 카카오 로그인하기
   override fun init() {
           super.init()
           binding.kakaoLoginButton.setOnClickListener {
                     kakaoLogin()
           }
   }
   
   private fun kakaoLogin() {
           Single.just(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext()))
               .flatMap { available ->
                   if (available) UserApiClient.rx.loginWithKakaoTalk(requireContext())
                   else UserApiClient.rx.loginWithKakaoAccount(requireContext())
               }
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({ token ->
                   getUserInfo()
                   Log.i(TAG, "로그인 성공 ${token.accessToken}")
               }, { error ->
                   Log.e(TAG, "로그인 실패", error)
               })
               .addToDisposable()
       }
   
   private fun getUserInfo() {
           UserApiClient.rx.me()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({ user ->
                   landingViewModel.login(
                       userNo = user.id,
                       username = user.kakaoAccount?.profile?.nickname,
                       email = user.kakaoAccount?.email,
                       profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                   )
               }, { error ->
                   Log.e(TAG, "사용자 정보 요청 실패", error)
               })
               .addToDisposable()
       }
   
   private fun Disposable.addToDisposable(): Disposable = addTo(disposables)
   ```

   코틀린 카카오 소셜 로그인 끝

### 📌 카카오톡 공유

- 기본 템플릿으로 카카오 링크를 카카오톡으로 보냅니다.
- 이때 메세지는 json형식의 파일로 보내지게 됩니다.
- 카카오톡을 통해 메시지 공유가 가능한지 확인하기 위해 먼저 `isKakaoLinkAvailable`를 호출하여 사용자 기기에 카카오톡이 설치되어 있는지 확인합니다.
- 카카오톡이 설치되어 있는 경우 `defaultTemplate`를 호출하여 카카오톡으로 메시지를 공유할 수 있도록 합니다.
- 카카오톡이 설치되어 있지 않다면 `WebSharerClient`의 `defaultTemplateUri`를 통해 공유용 URL을 선언한 후, 기본 브라우저나 웹뷰로 해당 URL을 열 수 있도록 구현합니다.

##### 🌵 안드로이드 카카오 링크 공유하기 구현

```kotlin
package com.kakao.sdk.common.util

import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.browser.customtabs.CustomTabsServiceConnection

/**
 * 간편한 CustomTabs 실행 기능을 제공합니다.
 */
object KakaoCustomTabsClient {

    @Throws(UnsupportedOperationException::class)
    fun openWithDefault(context: Context, uri: Uri): ServiceConnection? {
        val packageName = resolveCustomTabsPackage(
            context,
            uri
        ) ?: throw UnsupportedOperationException()
        SdkLog.d("Choosing $packageName as custom tabs browser")
        val connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) {
                val builder = CustomTabsIntent.Builder()
                        .enableUrlBarHiding().setShowTitle(true)
                val customTabsIntent = builder.build()
                customTabsIntent.intent.data = uri
                customTabsIntent.intent.setPackage(packageName)
                context.startActivity(customTabsIntent.intent)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                SdkLog.d("onServiceDisconnected: $name")
            }
        }
        val bound = CustomTabsClient.bindCustomTabsService(context, packageName, connection)
        return if (bound) connection else null
    }

    @Throws(ActivityNotFoundException::class)
    fun open(context: Context, uri: Uri) {
        CustomTabsIntent.Builder().enableUrlBarHiding().setShowTitle(true).build()
                .launchUrl(context, uri)
    }

    private fun resolveCustomTabsPackage(context: Context, uri: Uri): String? {
        var packageName: String? = null
        var chromePackage: String? = null
        // get ResolveInfo for default browser
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val resolveInfo = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val serviceIntent = Intent().setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
        val serviceInfos = context.packageManager.queryIntentServices(serviceIntent, 0)
        for (info in serviceInfos) {
            // check if chrome is available on this device
            if (chromePackage == null && isPackageNameChrome(
                    info.serviceInfo.packageName
                )
            ) {
                chromePackage = info.serviceInfo.packageName
            }
            // check if the browser being looped is the default browser
            if (info.serviceInfo.packageName == resolveInfo?.activityInfo?.packageName) {
                packageName = resolveInfo?.activityInfo?.packageName
                break
            }
        }
        if (packageName == null && chromePackage != null) {
            packageName = chromePackage
        }
        return packageName
    }

    private fun isPackageNameChrome(packageName: String): Boolean {
        return chromePackageNames.contains(packageName)
    }

    private val chromePackageNames = arrayOf(
            "com.android.chrome",
            "com.chrome.beta",
            "com.chrome.dev")
}
```



### 📌 Apple Login

-
