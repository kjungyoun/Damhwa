package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.request.SaveHistoryReq;
import com.ssafy.damhwa.api.service.HistoryService;
import com.ssafy.damhwa.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @PostMapping("/save")
    public ResponseEntity<? extends BaseResponse> saveMsgRecommHistory (@RequestBody SaveHistoryReq historyReq){
        long userno = historyReq.getUserno();
        String msg = historyReq.getMsg();
        String receiver = historyReq.getReceiver();
        int fno = historyReq.getFno();
        boolean htype = historyReq.isHtype();

        if(htype)
            historyService.createMsgHistoryList(fno,userno, msg, receiver);
        else
            historyService.createStateHistory(fno, userno, msg);

        return ResponseEntity.status(200).body(BaseResponse.of(200,"Save History Success"));
    }
}
