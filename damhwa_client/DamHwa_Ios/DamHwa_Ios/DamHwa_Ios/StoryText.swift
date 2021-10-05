//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct StoryText: View {
    
    @State var story = ""
    
    var body: some View {
        GeometryReader{ geometry in
        VStack{
            HStack{
                Text("서신 내용: \(story)")

                    .font(.custom("SangSangRockOTF", size: 20))
            }
                TextField("서신을 입력하세요", text: $story)
                    .keyboardType(/*@START_MENU_TOKEN@*/.default/*@END_MENU_TOKEN@*/)
                    .padding()
        }.frame(width: geometry.size.width, height: geometry.size.height)
            }
        }
    }





struct StoryText_Previews: PreviewProvider {
    static var previews: some View {
        StoryText()
    }
}
