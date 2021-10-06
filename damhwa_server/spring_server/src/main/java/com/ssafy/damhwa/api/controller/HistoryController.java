package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.request.SaveHistoryReq;
import com.ssafy.damhwa.api.service.HistoryService;
import com.ssafy.damhwa.common.response.BaseResponse;
import com.ssafy.damhwa.db.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    // 서신 History 저장 Controller
    @PostMapping("/save")
    public ResponseEntity<? extends BaseResponse> saveMsgRecommHistory (@RequestBody SaveHistoryReq historyReq){
        long userno = historyReq.getUserno();
        String msg = historyReq.getMsg();
        String receiver = historyReq.getReceiver();
        int fno = historyReq.getFno();
        boolean htype = historyReq.isHtype();
        historyService.createHistoryList(fno,userno, msg, receiver, htype);

        return ResponseEntity.status(200).body(BaseResponse.of(200,"Save History Success"));
    }

    @GetMapping("/search")
    public ResponseEntity<History> searchOneHistory(@RequestParam long hno){
        History history = historyService.getHistory(hno);

        return new ResponseEntity<History>(history, HttpStatus.OK);
    }

    // Timezone 오류 수정
//    @GetMapping("/test")
//    public ResponseEntity<Date> returnNow(){
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        calendar.setTime(date);
//        calendar.add(Calendar.HOUR,9);
//        System.out.println(calendar.getTime());
//        System.out.println(Calendar.getInstance().getTime());
//        return new ResponseEntity<Date>(calendar.getTime(),HttpStatus.OK);
//    }

}
