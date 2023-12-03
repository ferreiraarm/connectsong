package com.amf.connectsong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.amf.connectsong.model.Roulette;
import com.amf.connectsong.model.User;

@Repository
public interface RouletteRepository extends JpaRepository<Roulette, Long>{
    
    List<Roulette> findAll();
    Roulette findByUser(User user);
    Roulette findById(long id);
}
