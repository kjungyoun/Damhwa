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
            KakaoSDKCommon.initSDK(appKey: "5d4ceabed4218c89d458e28bfdd4ed60")
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
