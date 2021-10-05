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
    @State var headspace: CGFloat = 15
    @State var sidesScaling: CGFloat = 0.8
    @State var isWrap: Bool = false
    @State var autoScroll: Bool = false
    @State var time: TimeInterval = 1
    @State var currentIndex: Int = 0
    @State var msg: String
    @State var fArray = [Int]()
    @State var fImgArray = [String]()
    @State var indexArray: [Int] = [0]
    @State var check = 0
    
    @State var flowerImages = [Image](arrayLiteral: Image(uiImage: "".load()))
    
    var body: some View {
        NavigationView{
            VStack {
                
                Spacer().frame(height:30)
                Text("이야기")
                    .padding()
                    .font(.custom("SangSangRockOTF", size: 35))
                Text("\(currentIndex + 1)/\(fArray.count)")
                Spacer().frame(height: 40)
                NavigationLink(
                    destination: DetailFlower(index: currentIndex + 1, name: fArray[currentIndex]),
                    label: {
                        ACarousel(indexArray,
                                  id: \.self,
                                  index: $currentIndex,
                                  spacing: spacing,
                                  headspace: headspace,
                                  sidesScaling: sidesScaling,
                                  isWrap: !isWrap,
                                  autoScroll: autoScroll ? .active(time) : .inactive) { index in
                            flowerImages[index + 1]
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
        }.onAppear{
            if(check==0){
                for i in fImgArray{
                    flowerImages.append(Image(uiImage: "\(i)".load()))
                }
                for j in 1...fArray.count-1{
                    indexArray.append(j)
                }
                print(indexArray)
                print(fArray)
                print(fImgArray)
                check = 1
            }
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
        ContentView(msg:"",fArray:[],fImgArray:[])
    }
}




