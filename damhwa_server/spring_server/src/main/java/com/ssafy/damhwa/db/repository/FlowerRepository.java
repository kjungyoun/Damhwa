package com.ssafy.damhwa.db.repository;

import com.ssafy.damhwa.db.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    Optional<Flower> findByFno(int fno);
}
