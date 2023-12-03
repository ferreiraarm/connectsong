package com.amf.connectsong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amf.connectsong.model.Album;



@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    
    List<Album> findAll();
    Album findByNome(String nome);
    Album findById(long id);
     
}