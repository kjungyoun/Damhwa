//
//  Test.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/30.
//

import SwiftUI
import KakaoSDKLink
import KakaoSDKTemplate
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKTalk

struct Test: View {
    var body: some View {
        Button(action: Link, label: {
            /*@START_MENU_TOKEN@*/Text("Button")/*@END_MENU_TOKEN@*/
        })
        
    }
    
    func Link(){
        let title = "피드 메시지"
            let description = "피드 메시지 예제"

            let feedTemplateJsonStringData =
              """
              {
                  "objectType": "feed",
                  "content": {
                      "title": "딸기 치즈 케익",
                      "description": "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅",
                      "imageUrl": "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
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

            // templatable은 메시지 만들기 항목 참고

//        do{
//            let temp = try JSONDecoder().decode(FeedTemplate.self, from: feedTemplateJsonStringData)
//                print(temp)
//            }catch{
//                print(error)
//            }
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


struct Test_Previews: PreviewProvider {
    static var previews: some View {
        Test()
    }
}
