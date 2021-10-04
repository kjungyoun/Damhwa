//
//  ContentView.swift
//  ACarouselDemo iOS
//
//  Created by Autumn on 2020/11/16.
//

import SwiftUI
import ACarousel
               
struct Item: Identifiable {
    let id = UUID()
    let image: Image
}

let roles = ["Luffy", "Zoro", "Sanji", "Nami", "Usopp", "Chopper", "Robin", "Franky", "Brook"]
let testF = [241,214]

struct ContentView: View {
    
    @State var spacing: CGFloat = 10
    @State var headspace: CGFloat = 10
    @State var sidesScaling: CGFloat = 0.8
    @State var isWrap: Bool = false
    @State var autoScroll: Bool = false
    @State var time: TimeInterval = 1
    @State var currentIndex: Int = 0
    @State var msg: String
    @State var fArray = [Int]()
    
    var body: some View {
        NavigationView{
        VStack {
            
            Spacer().frame(height:30)
            Text("\(msg)")
            Text("이야기")
                .padding()
                .font(.custom("SangSangRockOTF", size: 35))
            Text("\(currentIndex + 1)/\(roles.count)")
            Spacer().frame(height: 40)
            NavigationLink(
                destination: DetailFlower(index: currentIndex + 1, name: roles[currentIndex]),
                label: {
                    ACarousel(roles,
                              id: \.self,
                              index: $currentIndex,
                              spacing: spacing,
                              headspace: headspace,
                              sidesScaling: sidesScaling,
                              isWrap: isWrap,
                              autoScroll: autoScroll ? .active(time) : .inactive) { name in
                        Image(name)
                            .resizable()
                            .frame(height: 500)
                            .cornerRadius(60)
                    }
                    .frame(height: 500)
                })
            Spacer()
            }
        .background(Color(red: 242 / 255, green: 238 / 255, blue: 229 / 255).edgesIgnoringSafeArea(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/))
        .ignoresSafeArea()
        }
    }
}



// swipe back 
extension UINavigationController: UIGestureRecognizerDelegate {
    override open func viewDidLoad() {
        super.viewDidLoad()
        interactivePopGestureRecognizer?.delegate = self
    }

    public func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return viewControllers.count > 1
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(msg:"",fArray:[])
    }
}




