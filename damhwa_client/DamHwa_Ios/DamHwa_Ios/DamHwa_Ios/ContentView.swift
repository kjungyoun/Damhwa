//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct ContentView: View {
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
                    
                    Text("담").tabItem {
                        Image(systemName: "list.dash")
                        Text("담")
                    }
                    Text("화").tabItem {
                        Image(systemName: "square.and.pencil")
                        Text("화")
                    }
                }
            }.navigationBarTitle("서신쓰기",displayMode: .inline)
            
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
