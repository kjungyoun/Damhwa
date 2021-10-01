package com.ssafy.damhwa.api.response;

import com.ssafy.damhwa.db.entity.Flower;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlowerNEmotionRes {
    Flower flower;
    String emotionResult;
}
