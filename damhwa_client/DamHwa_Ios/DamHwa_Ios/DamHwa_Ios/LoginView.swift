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
import KakaoSDKLink
import KakaoSDKTemplate
import SafariServices
import Alamofire

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

struct LoginView: View {
    @EnvironmentObject var authentication: Authentication
    @Environment(\.colorScheme) var colorScheme
    @State var name : String =  "before login"
    @State var alog : Bool = false
    @State private var isLoading = true
    
    var body: some View {
        ZStack{
            VStack{
                GeometryReader{geometry in
                    Image("img4").resizable().frame(width: geometry.size.width + 100, height: geometry.size.height, alignment: .center).opacity(0.4)
                }
            }
        VStack{
            Spacer().frame(height:200)
            Text("마음을 전하는\n가장 향기로운 방법")
                .padding()
                .font(.custom("SangSangRockOTF", size: 35))
                .frame(width: 400, alignment: .leading)
            Spacer()
            Text("담화")
                .padding()
                .font(.custom("SangSangRockOTF", size: 65))
            Spacer()
            SignInWithAppleButton(.signIn, onRequest: configure, onCompletion: handle)
                .signInWithAppleButtonStyle(colorScheme == .dark ? .white : .black)
                .frame(width:300, height: 45)
                .padding()
            
            Spacer().frame(height:10)
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
                UserApi.shared.accessTokenInfo {(accessTokenInfo, error) in
                    if let error = error {
                        print(error)
                    }
                    else {
                        print("accessTokenInfo() success.")
                        authentication.updateValidation(success: true)
                        //do something
                        let kakaoToken = accessTokenInfo
                        print(kakaoToken)
                    }
                }
                UserApi.shared.me() {(user, error) in
                    if let error = error {
                        print(error)
                    }
                    else {
                        print("me() success.")
                        
                        //do something
                        let userData = user?.kakaoAccount?.profile?.nickname
                        print(userData!)
                        let id = user?.id
                        print(id!)
                        let email = user?.kakaoAccount?.email
                        print(email!)
                        //                    self.name = userData!
                        let parameters = [
                            "userno" : "\(id!)", // Int로 보낼수 없음. Any가 encodable 이 아니라서...
                            "email" : "\(email!)",
                            "username" : "\(userData)",
                            "profile" : ""
                        ]
                        
                        AF.request("http://j5a503.p.ssafy.io:8080/auth/kakao/login",
                                   method: .post,
                                   parameters: parameters,
                                   encoder: JSONParameterEncoder.default).response { response in
                                    print(response)
                                   }
                    }
                }
                
            }){
                
                Image("kakao_login")
            }
            //ios가 버전이 올라감에 따라 sceneDelegate를 더이상 사용하지 않게되었다
            //그래서 로그인을 한후 리턴값을 인식을 하여야하는데 해당 코드를 적어주지않으면 리턴값을 인식되지않는다
            //swiftUI로 바뀌면서 가장큰 차이점이다.
            .onOpenURL(perform: { url in
                if (AuthApi.isKakaoTalkLoginUrl(url)) {
                    _ = AuthController.handleOpenUrl(url: url)
                }
                
            })
            Button(action: {
                authentication.updateValidation(success: true)
            }){
                Text("로그인없이 사용하기")
            }.padding()
            
            Spacer().frame(height:100)
        }
        .onAppear{
            if (AuthApi.hasToken()) {
                UserApi.shared.accessTokenInfo { (_, error) in
                    if let error = error {
                        print("error")
                        authentication.updateValidation(success: false)
                    }
                    else {
                        print("succeee")
                        authentication.updateValidation(success: true)
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    }
                }
            }
            else {
                //로그인 필요
            }
            if (alog){
                authentication.updateValidation(success: true)
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 4.0){
                isLoading = false
            }
            
        }
            if isLoading{
                ZStack{
                    Color(.black)
                        .opacity(1.0)
                        .ignoresSafeArea()
                    LottieView(filename: "simpleflower")
                        .frame(width: 200, height: 300, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    
                    //                            ProgressView()
                    //                                .progressViewStyle(CircularProgressViewStyle(tint: .red))
                    //                                .scaleEffect(3)
                }
                
            }
        
        }.background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
        .ignoresSafeArea()
        
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
                    alog = true
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
        LoginView()
        
    }
}
