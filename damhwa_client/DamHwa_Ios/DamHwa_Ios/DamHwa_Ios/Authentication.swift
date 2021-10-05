//
//  Authentication.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/10/03.
//

import SwiftUI
import KakaoSDKAuth


class Authentication: ObservableObject {
    @Published var isValidated = false
    
    func updateValidation(success: Bool){
        withAnimation {
            isValidated = success
        }
    }
}
