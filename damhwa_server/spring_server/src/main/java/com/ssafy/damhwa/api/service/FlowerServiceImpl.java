package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.Flower;
import com.ssafy.damhwa.db.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FlowerServiceImpl implements FlowerService{

    @Autowired
    FlowerRepository flowerRepository;

    @Override
    public List<Flower> getFlowersByfno(int[] fnos) {
        List<Flower> list = new LinkedList<Flower>();

        int size = fnos.length;
        for (int i=0; i<size; i++){
            Flower flower = flowerRepository.findByFno(fnos[i]).get();
            System.out.println(flower);
            list.add(flower);
        }
        return list;
    }

    @Override
    public Flower getFlowerByfno(int fno) {
        Optional<Flower> flower = flowerRepository.findByFno(fno);

        if(flower.isPresent())
            return flower.get();
        return null;
    }
}
