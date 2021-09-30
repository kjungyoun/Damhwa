package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.Flower;

import java.util.List;

public interface FlowerService {
    List<Flower> getFlowersByfno(int[] fnos);
    Flower getFlowerByfno(int fno);
}
