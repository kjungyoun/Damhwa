package com.ssafy.damhwa.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.damhwa.db.entity.History;
import com.ssafy.damhwa.db.entity.QHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    QHistory qHistory = QHistory.history;

    public History getHistoryByUserno(long userno){
        History history = jpaQueryFactory.select(qHistory).from(qHistory)
                .where(qHistory.userno.eq(userno).and(qHistory.htype.eq(false))).fetchOne();

        return history;
    }
}
