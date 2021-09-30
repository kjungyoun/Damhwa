//
//  WebViewPage.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/30.
//

import SwiftUI
import WebKit

struct WebViewPage: UIViewRepresentable {
    var urlToLoad: String
    
    // make ui view
    func makeUIView(context: Context) -> WKWebView {
        // unwrapping
        guard let url = URL(string: self.urlToLoad) else {
            return WKWebView()
        }
        
        //config webview instance
        let webview = WKWebView()
        
        //load webview
        webview.load(URLRequest(url: url))
        
        return webview
    }
    
    //update ui view
    func updateUIView(_ uiView: WKWebView, context:
        UIViewRepresentableContext<WebViewPage>) {
    
    }
}

struct WebViewPage_Previews: PreviewProvider {
    static var previews: some View {
        WebViewPage(urlToLoad: "https://www.naver.com")
    }
}
