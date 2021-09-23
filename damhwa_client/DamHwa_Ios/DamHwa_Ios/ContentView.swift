//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        let text = "hello, swiftui5"
        Text(text)
            .font(.largeTitle)
            .fontWeight(/*@START_MENU_TOKEN@*/.heavy/*@END_MENU_TOKEN@*/)
            .foregroundColor(.blue)
            .padding()
            
        Button(action: {
            print("hello")
        }) {
            Text("SwiftUI")
                .font(.title)
                .foregroundColor(.blue)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
