//
//  TextRecommend.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//
import AuthenticationServices
import SwiftUI
import KakaoSDKAuth
import KakaoSDKUser


struct AppleUser: Codable {
    let userId: String
    let firstName: String
    let lastName: String
    let identityToken: Data
    let email: String
    
    init?(credentials: ASAuthorizationAppleIDCredential){
        guard
            let firstName = credentials.fullName?.givenName,
            let lastName = credentials.fullName?.familyName,
            let identityToken = credentials.identityToken,
            let email = credentials.email
        else {return nil}
        
        self.userId = credentials.user
        self.firstName = firstName
        self.lastName = lastName
        self.email = email
        self.identityToken = identityToken
    }
}

struct TextRecommend: View {
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack{
        SignInWithAppleButton(.signIn, onRequest: configure, onCompletion: handle)
            .signInWithAppleButtonStyle(colorScheme == .dark ? .white : .black)
            .frame(height: 45)
            .padding()
        
        Spacer()
        Button(action : {
                    //카카오톡이 깔려있는지 확인하는 함수
                    if (UserApi.isKakaoTalkLoginAvailable()) {
                        //카카오톡이 설치되어있다면 카카오톡을 통한 로그인 진행
                        UserApi.shared.loginWithKakaoTalk {(oauthToken, error) in
                            print(oauthToken?.accessToken)
                            print(error)
                        }
                    }else{
                        //카카오톡이 설치되어있지 않다면 사파리를 통한 로그인 진행
                        UserApi.shared.loginWithKakaoAccount {(oauthToken, error) in
                            print(oauthToken?.accessToken)
                            print(error)
                        }
                    }
                }){
                    
                    Text("카카오 로그인")
                }
                //ios가 버전이 올라감에 따라 sceneDelegate를 더이상 사용하지 않게되었다
                //그래서 로그인을 한후 리턴값을 인식을 하여야하는데 해당 코드를 적어주지않으면 리턴값을 인식되지않는다
                //swiftUI로 바뀌면서 가장큰 차이점이다.
                .onOpenURL(perform: { url in
                    if (AuthApi.isKakaoTalkLoginUrl(url)) {
                        _ = AuthController.handleOpenUrl(url: url)
                    }
                })
        }
      }
    
    func configure(_ request: ASAuthorizationAppleIDRequest){
        request.requestedScopes = [.fullName, .email]
        
    }
    func handle(_ authResult: Result<ASAuthorization, Error>){
        switch authResult {
        case .success(let auth):
            print(auth)
            switch auth.credential {
            case let appleIdCredentials as ASAuthorizationAppleIDCredential:
                if let appleUser = AppleUser(credentials: appleIdCredentials),
                   let appleUserData = try? JSONEncoder().encode(appleUser){
                    UserDefaults.standard.setValue(appleUserData, forKey: appleUser.userId)
                    
                    print("saved apple user", appleUser)
                } else {
                    print("missing some fields", appleIdCredentials.email,
                          appleIdCredentials.fullName, appleIdCredentials.user)
                    
                    guard
                        let appleUserData = UserDefaults.standard.data(forKey: appleIdCredentials.user),
                        let appleUser = try? JSONDecoder().decode(AppleUser.self, from: appleUserData)
                    else{ return }
                    
                    print(appleUser)
                }
                
            default:
                print(auth.credential)
            }
            
        case .failure(let error):
            print(error)
        }
    }
}

struct TextRecommend_Previews: PreviewProvider {
    static var previews: some View {
        TextRecommend()
            
    }
}
