//
//  Webview.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/10/06.
//

import Foundation
import SwiftUI
import WebKit
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKLink

struct Webview: UIViewRepresentable {
    
    var url: String
    @State var userNo:Int64 = 0
    
    func makeUIView(context: Context) -> WKWebView {
        
        guard let url = URL(string: self.url) else {
            return WKWebView()
        }
        
        let request = URLRequest(url: url)
        let wkWebview = WKWebView()
   
        let sem = DispatchSemaphore(value: 0)
        
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
        
        let funcName = "sendUserNo(\(userNo));"
        wkWebview.load(request)
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0){
        wkWebview.evaluateJavaScript(funcName, completionHandler: { result, error in
            if let result = result {
                print("---")
                print(result)
            }else{
                print("0-0-0")
                print(error)
            }
            print(result)
            print(error)
            print("nono")
            
        })
        }
        return wkWebview
    }
    
    func updateUIView(_ uiView: WKWebView, context:
                        UIViewRepresentableContext<Webview>){
        
    }
}
