package com.t2p.persistence.repository;

import com.t2p.persistence.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer>{
    News findAllByUrl(String url);

}
