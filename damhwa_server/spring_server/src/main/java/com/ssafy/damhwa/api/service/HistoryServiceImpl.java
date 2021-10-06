package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.History;
import com.ssafy.damhwa.db.repository.HistoryRepository;
import com.ssafy.damhwa.db.repository.HistoryRepositorySupport;
import com.ssafy.damhwa.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    HistoryRepositorySupport historyRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    Date now = new Date();
    Calendar cal = Calendar.getInstance();

    @Override
    public void createHistoryList(int fno, long userno, String msg, String receiver, boolean htype) {

        // history에 저장
            History history = new History();
            history.setContents(msg);
            history.setUserno(userno);
            history.setHtype(htype);
            history.setReceiver(receiver);
            history.setFno(fno);

            historyRepository.save(history);
    }

    @Override
    public List<History> getHistoryList(long userno) {
        List<History> list = historyRepository.findAllByUserno(userno);
//        int size = list.size();
//        for (int i=0; i<size; i++){
//            History history = list.get(i);
//
//            // 시간 UTC timezone to KST
//            Calendar calendar = Calendar.getInstance();
//            Date date = history.getRegdate();
//            calendar.setTime(date);
//            calendar.add(Calendar.HOUR,9);
//            history.setRegdate(calendar.getTime());
//        }
        return list;
    }

    @Override
    public History getHistory(long hno) {
        Optional<History> result = historyRepository.findByHno(hno);
        History history = new History();
        if(result.isPresent()) {
            history = result.get();
//            // 시간 UTC timezone to KST
//            Calendar calendar = Calendar.getInstance();
//            Date date = history.getRegdate();
//            calendar.setTime(date);
//            calendar.add(Calendar.HOUR, 9);
//            history.setRegdate(calendar.getTime());
            return history;
        }
        else
            return null;
    }

}
