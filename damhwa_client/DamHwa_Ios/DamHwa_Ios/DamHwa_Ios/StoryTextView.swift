//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI
//import KakaoSDKAuth
import Alamofire

extension View {
    func dismissKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct Mm: Encodable {
    let msg: String
}

struct Flower: Codable {
    let fno:Int
    let fname_KR:String
    let fname_EN:String
    let fmonth:Int
    let fday:Int
    let flang:String
    let fcontents:String
    let fgrow:String
    let img1:String
    let watercolor_img:String

    
}



struct StoryTextView: View {
    
    @State var story = ""
    @State var receiver = ""
    @State private var shouldTransit: Bool = false
    @State var fnoArr = [Int]()
    @State var fimgArr = [String]()
    @State var fnameArr = [String]()
    @State private var isLoading = false
    
    var body: some View {
        HStack {
            NavigationView{
                ZStack{
                    VStack{
                        Spacer()
                        Image("img1").resizable().frame(width: 400, height: 700, alignment: .bottomTrailing).opacity(/*@START_MENU_TOKEN@*/0.8/*@END_MENU_TOKEN@*/)
                    }
                    VStack{
                        Spacer().frame(height:30)
                        Text("서신 쓰기")
                            .padding()
                            .font(.custom("SangSangRockOTF", size: 35))
                            .frame(width:2000)
                        Text("상대방에게 전할 서신을 적어주세요\n서신에 맞는 예쁜 꽃을 추천해드릴게요")
                            .padding()
                            .frame(width:400, alignment:.topLeading)
                            .font(.custom("SangSangRockOTF", size: 20))
                        Text("   받는이")
                            .font(.custom("SangSangRockOTF", size: 20))
                            .frame(width:400, alignment:.topLeading)
                        TextEditor(text: $receiver)
                            .keyboardType(.default)
                            .frame(width: 360, height: 40, alignment: .topLeading)
                            .background(Color.white.opacity(0.5))
                            .font(.custom("SangSangRockOTF", size: 20))
                            .opacity(/*@START_MENU_TOKEN@*/0.8/*@END_MENU_TOKEN@*/)
                        Text("   서신을 작성하시오")
                            .font(.custom("SangSangRockOTF", size: 20))
                            .frame(width:400, alignment:.topLeading)
                        TextEditor(text: $story)
                            .keyboardType(.default)
                            .frame(width: 360, height: 400, alignment: .topLeading)
                            .background(Color.white.opacity(0.5))
                            .font(.custom("SangSangRockOTF", size: 20))
                            .opacity(/*@START_MENU_TOKEN@*/0.8/*@END_MENU_TOKEN@*/)
                        Spacer()
                        NavigationLink(destination: ContentView(msg:"\(story)",fArray:fnoArr,fImgArray:fimgArr,fNameArray:fnameArr, receiver:"\(receiver)").navigationBarHidden(true), isActive: $shouldTransit){
                            Image("recommButton").resizable().frame(width:120, height:40)
                                .onTapGesture {
                                    isLoading = true
                                    post()
                                }.opacity(story.isEmpty ? 0.5 : 1.0)
                        }.disabled(story.isEmpty)
                        Spacer().frame(height:100)
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
                    
                }.navigationBarTitle("서신쓰기", displayMode: .inline)
                .navigationBarHidden(true)
                .background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
                .ignoresSafeArea()
                
            }.onTapGesture {
                self.dismissKeyboard()
            }
        }
    }
    func post(){
        let mm = Mm(msg: "\(story)")
        
        AF.request("http://j5a503.p.ssafy.io:8080/api/recomm/msg",
                   method: .post,
                   parameters: mm,
                   encoder: JSONParameterEncoder.default).response { response in
                    guard let data = String(bytes: response.value!!, encoding: .utf8)else{return}
                    let data2 = Data(data.utf8)
                    
                    do {
                        let f = try JSONDecoder().decode([Flower].self, from: data2)
                        for i in f{
                            fnoArr.append(i.fno)
                            fimgArr.append(i.watercolor_img)
                            fnameArr.append(i.fname_KR)
                        }
                        print(fnoArr)
                        print(fimgArr)
                        self.shouldTransit = true
                        isLoading = false
                        
                    } catch {
                        print(error)
                    }
                    
                   }
        
        print("ddd")
    }
    
}



struct StoryTextView_Previews: PreviewProvider {
    static var previews: some View {
        StoryTextView()
    }
}
