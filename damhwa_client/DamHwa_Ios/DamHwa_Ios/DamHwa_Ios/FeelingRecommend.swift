//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct FeelingRecommend: View {
    
    @State var feeling = ""
    
    var body: some View {
        HStack {
            NavigationView{
                VStack {
                    Text("오늘의 기분이 어떤지 적어보세요: \(feeling)")
                        .padding()
                    TextField("기분을 입력하세요", text: $feeling)
                        .keyboardType(/*@START_MENU_TOKEN@*/.default/*@END_MENU_TOKEN@*/)
                    NavigationLink(destination: TextRecommend()){
                        Text("꽃으로 전하기")
                    }
                }.navigationBarTitle("감정쓰기",displayMode: .inline)
                
            }
        }
        
    }
}

struct FeelingRecommend_Previews: PreviewProvider {
    static var previews: some View {
        FeelingRecommend()
    }
}
