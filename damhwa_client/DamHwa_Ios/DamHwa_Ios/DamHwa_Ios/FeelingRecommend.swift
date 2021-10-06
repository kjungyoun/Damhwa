//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI
import Alamofire
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKLink

struct Sm: Encodable {
    let state: String
    let userno: Int
    
}

struct Flower3: Codable {
    let fno:Int
    let fname_KR:String
    let fname_EN:String
    let fmonth:Int
    let fday:Int
    let flang:String
    let fcontents:String
    let fuse:String
    let fgrow:String
    let img1:String
    let watercolor_img:String
    let emotionResult:String
}

struct FeelingRecommend: View {
    
    @State var feeling = ""
    @State private var shouldTransit: Bool = false
    @State var fname = 0
    @State var femotion = ""
    @State private var isLoading = false
    @State var userNo:Int64 = 0
    
    
    var body: some View {
        HStack {
            NavigationView{
                ZStack{
                    VStack{
                        Spacer()
                        Image("img1").resizable().frame(width: 400, height: 700, alignment: .bottomTrailing).opacity(0.6)
                    }
                    VStack {
                        Spacer().frame(height:30)
                        Text("오늘의 감정")
                            .padding()
                            .font(.custom("SangSangRockOTF", size: 35))
                            .frame(width:2000)
                        Text("오늘의 기분이 어떤지 적어주세요\n예쁜 꽃을 추천해드릴게요")
                            .padding()
                            .frame(width:400, alignment:.topLeading)
                            .font(.custom("SangSangRockOTF", size: 20))
                        Text("추천은 하루에 한번만 가능하니 신중하게 적어주세요~!")
                            .padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 0))
                            .frame(width:400, alignment:.topLeading)
                            .font(.custom("SangSangRockOTF", size: 15))
                        TextEditor(text: $feeling)
                            .keyboardType(.webSearch)
                            .frame(width: 360, height: 400, alignment: .topLeading)
                            .background(Color.white.opacity(0.5))
                            .font(.custom("SangSangRockOTF", size: 20))
                            .opacity(/*@START_MENU_TOKEN@*/0.8/*@END_MENU_TOKEN@*/)
                        Spacer()
                        NavigationLink(destination: EmotionDetail(name: fname,femotion:"\(femotion)",fmsg: feeling).navigationBarHidden(true), isActive: $shouldTransit){
                            Image("feelRecommButton").resizable().frame(width:120, height:40)
                                .onTapGesture {
                                    isLoading = true
                                    post()
                                }.opacity(feeling.isEmpty ? 0.5 : 1.0)
                        }.disabled(feeling.isEmpty)
                        
                        Spacer()
                    }
                    
                    if isLoading{
                        ZStack{
                            Color(.black)
                                .opacity(0.5)
                                .ignoresSafeArea()
                            VStack{
                                Spacer()
                                Text("텍스트를 변환하고 있습니다.")
                                    .font(.custom("SangSangRockOTF", size: 25))
                                    .foregroundColor(.white)
                                Spacer()
                                Text("입력하신 데이터에 따라서\n결과물이 정확하지 않을 수 있습니다.")
                                    .font(.custom("SangSangRockOTF", size: 15))
                                    .foregroundColor(.white)
                                Spacer()
                            }
                            
                            LottieView(filename: "simpleflower")
                                .frame(width: 200, height: 300, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                            
                            //                            ProgressView()
                            //                                .progressViewStyle(CircularProgressViewStyle(tint: .red))
                            //                                .scaleEffect(3)
                        }
                        
                    }
                    
                }.navigationBarTitle("감정쓰기",displayMode: .inline)
                .navigationBarHidden(true)
                .background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
                .ignoresSafeArea()
                
            }.onTapGesture {
                self.dismissKeyboard()
            }
        }
        
    }
    func post(){
        
        UserApi.shared.me() {(user, error) in
            if let error = error {
                print(error)
            }
            else {
                print("me() success.")
                userNo = (user?.id!)!
                print(userNo)
            }
        }
        
        let mm = Sm(state: "\(feeling)", userno: Int(userNo))
        
        AF.request("http://j5a503.p.ssafy.io:8080/api/recomm/state",
                   method: .post,
                   parameters: mm,
                   encoder: JSONParameterEncoder.default).response { response in
                    guard let data = String(bytes: response.value!!, encoding: .utf8)else{return}
                    let data2 = Data(data.utf8)
                    
                    do {
                        let f = try JSONDecoder().decode(Flower3.self, from: data2)
                        print(f)
                        fname = f.fno
                        femotion = f.emotionResult
                        self.shouldTransit = true
                        isLoading = false
                    } catch {
                        print(error)
                    }
                    
                   }
        
    }
}

struct FeelingRecommend_Previews: PreviewProvider {
    static var previews: some View {
        FeelingRecommend()
    }
}
