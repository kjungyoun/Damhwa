//
//  DetailFlower.swift
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

struct Flower2: Codable {
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
    
}

extension String{
    func load() -> UIImage {
        
        do{
            guard let url = URL(string: self)else{
                return UIImage()
            }
            
            let data: Data = try
                Data(contentsOf: url)
            
            return UIImage(data: data)
                ?? UIImage()
        }catch{
            
        }
        return UIImage()
    }
    
}

struct DetailFlower: View {
    var index: Int
    @State var name: String
    @State var fname: String = ""
    @State var fdesc: String = ""
    @State var flang: String = ""
    @State var fimg: String = ""
    @State var desc = "dkdkdkdkdkdkdkkdk"
    
    var body: some View {
        NavigationView{
        VStack{
            Spacer().frame(height:30)
            Text("\(fname)")
                .padding()
                .font(.custom("SangSangRockOTF", size: 35))
                .frame(width:2000)
            Image(uiImage: "\(fimg)".load())
                .resizable()
                .frame(width: 350, height: 400)
                .cornerRadius(60)
                .padding()
            
            Text("\(fdesc)")
                .frame(width: 360, height: 150, alignment: .topLeading)
                .background(Color.white.opacity(0.5))
                .padding()
                .font(.custom("SangSangRockOTF", size: 15))
            
            Button(action: Link, label: {
                Text("공유하기")
            })
            Spacer()
        }.background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255)
        .edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
        .ignoresSafeArea()
        }.onAppear{
            do {
                let url = URL(string: "http://j5a503.p.ssafy.io:8080/flower/detail/?fno=241")
                let response = try String(contentsOf: url!)
                
                let data2 = Data(response.utf8)
                do {
                    let f = try JSONDecoder().decode(Flower2.self, from: data2)
                    print(f)
                    fname = f.fname_KR
                    fdesc = f.fcontents
                    flang = f.flang
                    fimg = f.img1
                } catch {
                    print("----")
                    print(error)
                }
                
                
            } catch let error as NSError {
                print(error.localizedDescription)
            }
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
                      "description": "\(flang)",
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
                  },
                  "buttons": [
                      {
                          "title": "웹으로 보기",
                          "link": {
                              "mobile_web_url": "https://developers.kakao.com",
                              "web_url": "https://developers.kakao.com"
                          }
                      },
                      {
                          "title": "앱으로 보기",
                          "link": {
                              "android_execution_params": "key1=value1&key2=value2",
                              "ios_execution_params": "key1=value1&key2=value2"
                          }
                      }
                  ]
              }
              """.data(using: .utf8)!

            if let templatable = try? JSONDecoder().decode(FeedTemplate.self, from: feedTemplateJsonStringData) {
              LinkApi.shared.defaultLink(templatable: templatable) { linkResult, error in
                if let error = error {
                  print(error)
                }
                else {
                  print("defaultLink() success.")

                  if let linkResult = linkResult {
                    UIApplication.shared.open(linkResult.url, options: [:], completionHandler: nil)
                  }
                }
              }
            }
    }
}

struct DetailFlower_Previews: PreviewProvider {
    static var previews: some View {
        DetailFlower(index:5, name:"Luffy")
    }
}
