package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.History;
import com.ssafy.damhwa.db.repository.HistoryRepository;
import com.ssafy.damhwa.db.repository.HistoryRepositorySupport;
import com.ssafy.damhwa.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public void createMsgHistoryList(int fno, long userno, String msg, String receiver) {
        // history에 저장
            History history = new History();
            history.setContents(msg);
            history.setUserno(userno);
            history.setHtype(true);
            history.setReceiver(receiver);
            history.setFno(fno);

            historyRepository.save(history);
    }

    @Override
    public void createStateHistory(int fno, long userno, String state) {
        History history = historyRepositorySupport.getHistoryByUserno(userno);
        if(history == null){
            history = new History();
            history.setHtype(false);
            history.setUserno(userno);
            history.setContents(state);
            history.setFno(fno);
        }else{
            history.setFno(fno);
            history.setContents(state);
        }

        historyRepository.save(history);
    }

    @Override
    public List<History> getHistoryList(long userno) {
        List<History> list = historyRepository.findAllByUserno(userno);
        return list;
    }

    @Override
    public History getHistory(long hno) {
        Optional<History> result = historyRepository.findByHno(hno);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

}
