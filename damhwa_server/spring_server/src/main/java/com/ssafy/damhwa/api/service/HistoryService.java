package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.History;

import java.util.List;

public interface HistoryService {
    void createMsgHistoryList(int[] fnos, String email, String msg, String receiver);
    void createStateHistory(int fno, String email, String state);
    List<History> getHistoryList(long userno);
}
