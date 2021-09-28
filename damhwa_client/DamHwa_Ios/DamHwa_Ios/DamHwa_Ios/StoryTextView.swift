//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct StoryTextView: View {
    
    @State var story = ""
    
    var body: some View {
        NavigationView{
            VStack{
                Spacer().frame(height:50)
                Text("서신 내용: \(story)")
                    .padding()
                    .font(.custom("SangSangRockOTF", size: 25))
                Spacer()
                TextField("서신을 입력하세요", text: $story)
                    .keyboardType(/*@START_MENU_TOKEN@*/.default/*@END_MENU_TOKEN@*/)
                    .padding()
                Spacer()
                NavigationLink(destination: ContentView()){
                    Text("꽃으로 전하기")
                        .padding()
                        .font(.custom("SangSangRockOTF", size: 30))
                }
                Spacer().frame(height:100)
            }.navigationBarTitle("서신쓰기", displayMode: .inline)
            
        }
    }
}




struct StoryTextView_Previews: PreviewProvider {
    static var previews: some View {
        StoryTextView()
    }
}
