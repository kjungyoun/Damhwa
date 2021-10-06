//
//  EmotionDetail.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/10/01.
//

import SwiftUI
import KakaoSDKLink
import KakaoSDKTemplate
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKTalk
import Alamofire

struct Fm: Encodable {
    let fno: Int
    let userno: Int
    let msg: String
    let receiver: String
    let htype: Bool
}


struct EmotionDetail: View {
    @State var name: Int
    @State var fname: String = ""
    @State var fdesc: String = ""
    @State var flang: String = ""
    @State var fimg: String = ""
    @State var desc = ""
    @State var femotion = ""
    @State var fmsg: String = ""
    @State var fno: Int = 0
    
    @State var kakaoId: Int64 = 0
    
    var body: some View {
        NavigationView{
            VStack{
                Spacer().frame(height:50)
                Text("조금 \(femotion) 당신 \n오늘은 \(fname) 어때요??")
                    .padding(.horizontal,16)
                    .font(.custom("SangSangRockOTF", size: 30))
                    .frame(width: 400, alignment: .leading)
                HStack{
                }.frame(width: 2000, height: 1, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                Image(uiImage: "\(fimg)".load())
                    .resizable()
                    .frame(width: 300, height: 350)
                    .cornerRadius(60)
                    .padding()
                
                Text("\(fdesc)")
                    .frame(width: 360, height: 150, alignment: .topLeading)
                    .padding(4)
                    .background(Color.white.opacity(0.5))
                    .font(.custom("SangSangRockOTF", size: 15))
                
                Button(action: Link, label: {
                    Image("shareButton").resizable().frame(width:120, height:100)
                        .padding()
                })
                Spacer()
            }.background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255)
                            .edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
            .ignoresSafeArea()
        }.onAppear{
            do {
                let url = URL(string: "http://j5a503.p.ssafy.io:8080/flower/detail/?fno=\(name)")
                let response = try String(contentsOf: url!)
                
                let data2 = Data(response.utf8)
                do {
                    let f = try JSONDecoder().decode(Flower2.self, from: data2)
                    print(f)
                    fname = f.fname_KR
                    fdesc = f.fcontents
                    flang = f.flang
                    fimg = f.watercolor_img
                    fno = f.fno
                    
                } catch {
                    print("----")
                    print(error)
                }
                
                
            } catch let error as NSError {
                print(error.localizedDescription)
            }
            
//            let mm = Fm(fno: fno, userno:Int(kakaoId), msg:"\(fmsg)", receiver:"", htype:false)
//
//            AF.request("http://j5a503.p.ssafy.io:8080/history/save",
//                       method: .post,
//                       parameters: mm,
//                       encoder: JSONParameterEncoder.default).response { response in
//                        print(response)
//                       }
            print("ddd")
//            DispatchQueue.main.asyncAfter(deadline: .now() + 1.0){
//                showAlert = false
//            }
            
        }
    }
    func Link(){
        let title = "피드 메시지"
        let description = "피드 메시지 예제"
        
        let feedTemplateJsonStringData =
            """
              {
                  "objectType": "feed",
                  "content": {
                      "title": "\(fname)",
                      "description": "꽃말:\(flang) 메세지:\(fmsg)",
                      "imageUrl": "\(fimg)",
                      "link": {
                          "mobile_web_url": "https://developers.kakao.com",
                          "web_url": "https://developers.kakao.com"
                      }
                  },
                  "social": {
                      "comment_count": 45,
                      "like_count": 286,
                      "shared_count": 845
                  }
              }
              """.data(using: .utf8)!
        
        if let templatable = try? JSONDecoder().decode(FeedTemplate.self, from: feedTemplateJsonStringData) {
            LinkApi.shared.defaultLink(templatable: templatable) { linkResult, error in
                if let error = error {
                    print(error)
                }
                else {
                    print("defaultLink() success.")
                    UserApi.shared.me() {(user, error) in
                        if let error = error {
                            print(error)
                        }
                        else {
                            kakaoId = (user?.id)!
                            print(kakaoId)
                        }
                    }
                    
                    
                    
                    // userapi.shared.me 카카오 유저넘버 필요
                    // history 저장하는 과정 필요
                    
                    if let linkResult = linkResult {
                        UIApplication.shared.open(linkResult.url, options: [:], completionHandler: nil)
                    }
                }
            }
        }
    }
}

struct EmotionDetail_Previews: PreviewProvider {
    static var previews: some View {
        EmotionDetail(name:0,femotion:"")
    }
}
