import SwiftUI

struct FlowerCalendar: View {
    var body: some View{
        Webview(url: "http://192.168.55.65:8081/")
    }
}

struct FlowerCalendar_Previews:PreviewProvider {
    static var previews: some View{
        FlowerCalendar()
    }
}
