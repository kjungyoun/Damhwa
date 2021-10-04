//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI
import KakaoSDKAuth
import Alamofire

extension View {
    func dismissKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct StoryTextView: View {
    
    @State var story = ""
    @State private var shouldTransit: Bool = false
    @State var fnoArr = [Int]()
    
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
                    TextEditor(text: $story)
                        .keyboardType(.webSearch)
                        .frame(width: 360, height: 400, alignment: .topLeading)
                        .background(Color.white.opacity(0.5))
                        .font(.custom("SangSangRockOTF", size: 20))
                        .opacity(/*@START_MENU_TOKEN@*/0.8/*@END_MENU_TOKEN@*/)
                    Spacer()
                    NavigationLink(destination: ContentView(msg:"\(story)",fArray:fnoArr).navigationBarHidden(true), isActive: $shouldTransit){
                        Image("recommButton").resizable().frame(width:120, height:40)
                            .onTapGesture {
                                post()
                                self.shouldTransit = true
                            }
                    }
                    Spacer().frame(height:100)
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
    func post() {
        let mm = Mm(msg: story)
        
        AF.request("http://j5a503.p.ssafy.io:8080/api/recomm/msg",
                   method: .post,
                   parameters: mm,
                   encoder: JSONParameterEncoder.default).response { response in
                    guard let data = String(bytes: response.value!!, encoding: .utf8)else{return}
                    
                    
                    
                    let data2 = Data(data.utf8)
                    do {
                        let f = try JSONDecoder().decode([Flower].self, from: data2)
                        print(f)
                        for i in f{
                            print(i)
                            fnoArr.append(i.fno)
                        }
                        print(fnoArr)
                    } catch {
                        print(error)
                    }
                    print("------")
                    print("\(data)")
             
                   }
        }
    
}



struct StoryTextView_Previews: PreviewProvider {
    static var previews: some View {
        StoryTextView()
    }
}
