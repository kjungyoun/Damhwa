//
//  DamHwa_IosApp.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI
import KakaoSDKCommon
import KakaoSDKAuth

@main
struct DamHwa_IosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    init() {
            // Kakao SDK 초기화
            KakaoSDKCommon.initSDK(appKey: "d589009de9e8118fb0628a19a05180d8")
        }
    
    @StateObject var authentication = Authentication()
    var body: some Scene {
        WindowGroup {
            if !authentication.isValidated {
                TextRecommend()
                    .environmentObject(authentication)
            }else {
                Home()
                    .environmentObject(authentication)

            }
        }
    }
}
