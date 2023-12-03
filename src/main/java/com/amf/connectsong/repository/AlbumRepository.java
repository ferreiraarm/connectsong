package com.amf.connectsong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amf.connectsong.model.Album;
import com.amf.connectsong.model.Roulette;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAll();

    Album findByName(String name);

    Album[] findAllByRoulette(Roulette roulette);

    Album findById(long id);

}