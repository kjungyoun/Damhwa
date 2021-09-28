//
//  ContentView.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/09/23.
//

import SwiftUI

struct FlowerCalendar: View {
    
    @State var text : String =  "before test"
    
    func post() {
        let text = self.text
        let param = "text=\(text)"
        let paramData = param.data(using: .utf8)
        
        let url = URL(string: "https://e1804655-4546-4e34-9b59-9f47db9b121c.mock.pstmn.io/test3")
        
        var request = URLRequest(url: url!)
                request.httpMethod = "POST"
                request.httpBody = paramData
        
        request.addValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
                request.setValue(String(paramData!.count), forHTTPHeaderField: "Content-Length")
       
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
                    // 서버가 응답 없거나 통신이 실패했을때
                    if let e = error {
                        NSLog("An error has occerred: \(e.localizedDescription)")
                        return
                    }
                    // 응답이 있을때 응답 처리 로직
                    // (1) 메인 스레드에서 비동기로 처리되도록 한다
                    DispatchQueue.main.async() {
                        do {
                            let object = try JSONSerialization.jsonObject(with: data!, options: []) as? NSDictionary
                                guard let jsonObject = object else { return }
                            
                            // (2) JSON 결과값을 추출한다.
                            let result = jsonObject["msg"] as? String
                            
                            // (3) 결과가 성공일 때에만 텍스트 뷰에 출력한다.
                            if result == "success" {
                                self.text = "요청결과: \(result!)" + "\n"
                            }
                        } catch let e as NSError {
                            print("An error has occured while parsing JSON Obejt : \(e.localizedDescription)")
                        }
                    }
                }
                
                // 6. POST 전송
                task.resume()
        }
    
    var body: some View {
        NavigationView{
            VStack{
                TextField("text",text:$text)
                Button(action: post) {
                    Text("POST test")
                }
                Text(text)
            }
        }
        
    }
}

struct FlowerCalendar_Previews: PreviewProvider {
    static var previews: some View {
        FlowerCalendar()
    }
}
