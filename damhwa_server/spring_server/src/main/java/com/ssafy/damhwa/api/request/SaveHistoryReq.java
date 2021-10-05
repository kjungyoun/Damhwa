package com.ssafy.damhwa.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveHistoryReq {
    String msg;
    long userno;
    int fno;
    String receiver;
    boolean htype;
}
