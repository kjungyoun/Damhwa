import SwiftUI

struct FlowerCalendar: View {
    var body: some View{
        Webview(url: "http://j5a503.p.ssafy.io:5000/").ignoresSafeArea()
    }
}

struct FlowerCalendar_Previews:PreviewProvider {
    static var previews: some View{
        FlowerCalendar()
    }
}
