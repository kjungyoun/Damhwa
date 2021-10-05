package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.History;

import java.util.List;

public interface HistoryService {
    void createMsgHistoryList(int fno, long userno, String msg, String receiver);
    void createStateHistory(int fno, long userno, String state);
    List<History> getHistoryList(long userno);
    History getHistory(long hno);
}
