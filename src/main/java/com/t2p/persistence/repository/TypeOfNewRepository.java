package com.t2p.persistence.repository;

import com.t2p.persistence.entity.TypeOfNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfNewRepository extends JpaRepository<TypeOfNews,Integer>{
}
