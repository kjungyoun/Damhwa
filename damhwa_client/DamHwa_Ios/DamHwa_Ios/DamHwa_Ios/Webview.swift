//
//  Webview.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/10/06.
//

import Foundation
import SwiftUI
import WebKit

struct Webview: UIViewRepresentable {
    
    var url: String
    
    func makeUIView(context: Context) -> WKWebView {
        
        guard let url = URL(string: self.url) else {
            return WKWebView()
        }
        
        let request = URLRequest(url: url)
        let wkWebview = WKWebView()
   
        let funcName = "sendUserNo(123123);"
        let temp = 19212441
        wkWebview.load(request)
        
        wkWebview.evaluateJavaScript(funcName, completionHandler: { result, error in
            if let result = result {
                print("---")
                print(result)
            }else{
                print("0-0-0")
                print(error)
            }
                            
            print("nono")
        })
        return wkWebview
    }
    
    func updateUIView(_ uiView: WKWebView, context:
                        UIViewRepresentableContext<Webview>){
        let wkWebview = WKWebView()
        let funcName = "sendUserNo(123123);"
        
        wkWebview.evaluateJavaScript(funcName, completionHandler: { result, error in
            if let result = result {
                print("---")
                print(result)
            }else{
                print("0-0-0")
                print(error)
            }
                            
            print("nono")
        })
        
        
    }
}
