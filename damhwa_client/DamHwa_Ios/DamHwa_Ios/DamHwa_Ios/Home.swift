
import SwiftUI


struct Home: View {
    
    @State var story = ""

    
    var body: some View {
            TabView {
                StoryTextView().tabItem {
                    Image(systemName: "paintbrush.pointed.fill")
                    Text("서신")
                }
                FeelingRecommend().tabItem {
                    Image(systemName: "face.smiling.fill")
                    Text("감정")
                }
                FlowerCalendar().tabItem {
                    Image(systemName: "calendar")
                    Text("달력")
                }
            }

        }
        
    }

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        Home()
    }
}
