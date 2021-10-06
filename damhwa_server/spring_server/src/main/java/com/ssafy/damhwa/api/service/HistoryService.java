package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.History;

import java.util.List;

public interface HistoryService {
    void createHistoryList(int fno, long userno, String msg, String receiver, boolean htype);
    List<History> getHistoryList(long userno);
    History getHistory(long hno);
}
