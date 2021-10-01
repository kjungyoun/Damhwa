//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct StoryTextView: View {
    var body: some View {
        NavigationView{
            VStack {
                Text("Hello, world!")
                    .padding()
                TextField("Placeholder"/*@END_MENU_TOKEN@*/, text: /*@START_MENU_TOKEN@*//*@PLACEHOLDER=Value@*/.constant(""))
                NavigationLink(destination: TextRecommend()){
                    Text("꽃으로 전하기")
                }
                TabView {
                    
                    Text("서신").tabItem {
                        Image(systemName: "square.and.pencil")
                        Text("서신")
                    }
                    Text("감정").tabItem {
                        Image(systemName: "square.and.pencil")
                        Text("감정")
                    }
                    Text("달력").tabItem {
                        Image(systemName: "calendar")
                        Text("달력")
                    }
                }
            }.navigationBarTitle("서신쓰기",displayMode: .inline)
            
        }
        
    }
}

struct StoryTextView_Previews: PreviewProvider {
    static var previews: some View {
        StoryTextView()
    }
}
