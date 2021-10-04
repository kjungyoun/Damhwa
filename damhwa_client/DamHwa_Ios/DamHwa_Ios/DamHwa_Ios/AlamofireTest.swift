//
//  AlamofireTest.swift
//  DamHwa_Ios
//
//  Created by minkpang on 2021/10/03.
//

import SwiftUI
import Alamofire

struct Mm: Encodable {
    let msg: String
}

struct Flower: Codable {
    let fno:Int
    let fname_KR:String
    let fname_EN:String
    let fmonth:Int
    let fday:Int
    let flang:String
    let fcontents:String
    let fgrow:String
    
}

struct AlamofireTest: View {
    var body: some View {
        HStack{
            Button(action: {
            AF.request("http://j5a503.p.ssafy.io:8080/flower/detail/?fno=241"
,
                               method: .get,
                               parameters: nil,
                               encoding: JSONEncoding.default,
                               headers: ["Content-Type":"application/json", "Accept":"application/json"])
                        .validate(statusCode: 200..<300)
                        .responseJSON { (json) in
                            //여기서 가져온 데이터를 자유롭게 활용하세요.
                            let json2 = String(data:json.data!,encoding: .utf8)
                            print(json2)
                            
                    }
            
        }, label: {
            /*@START_MENU_TOKEN@*/Text("Button")/*@END_MENU_TOKEN@*/
        })
            Button(action: {
            
                let mm = Mm(msg: "testmsg")
                
                AF.request("http://j5a503.p.ssafy.io:8080/api/recomm/msg",
                           method: .post,
                           parameters: mm,
                           encoder: JSONParameterEncoder.default).response { response in
                            guard let data = String(bytes: response.value!!, encoding: .utf8)else{return}
                            print("\(data)")

                            
                }
            }, label: {
                Text("Button2")
            })
        }.onAppear{
            let mm = Mm(msg: "testmsg")
            
            AF.request("http://j5a503.p.ssafy.io:8080/api/recomm/msg",
                       method: .post,
                       parameters: mm,
                       encoder: JSONParameterEncoder.default).response { response in
                        guard let data = String(bytes: response.value!!, encoding: .utf8)else{return}
                        
                        var fnoArr = [Int]()
                        
                        let data2 = Data(data.utf8)
                        do {
                            let f = try JSONDecoder().decode([Flower].self, from: data2)
                            print(f)
                            for i in f{
                                print(i)
                                fnoArr.append(i.fno)

                            }
                            print(fnoArr)
                        } catch {
                            print(error)
                        }
                        print("------")
                        print("\(data)")
                 
            }
        }
    }
}

struct AlamofireTest_Previews: PreviewProvider {
    static var previews: some View {
        AlamofireTest()
    }
}
