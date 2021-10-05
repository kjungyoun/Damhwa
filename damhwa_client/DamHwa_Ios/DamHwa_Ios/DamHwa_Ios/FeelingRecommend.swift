//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI
import Alamofire

struct Sm: Encodable {
    let state: String
    
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
                            Color(.systemBackground)
                                .ignoresSafeArea()
                            LottieView(filename: "simpleflower")
                                .ignoresSafeArea()
                            
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
        let mm = Sm(state: "\(feeling)")
        
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
