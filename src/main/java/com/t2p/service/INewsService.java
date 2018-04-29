package com.t2p.service;

import com.t2p.persistence.entity.News;
import com.t2p.persistence.entity.TypeOfNews;

import java.util.List;

public interface INewsService {

    News getNewsById(Integer id);

    News getNewsByUrl(String url);

    List<News> getAllNews();

    List<News> getAllNewsByType(TypeOfNews type);

    boolean deleteNewsById(Integer id);

    News addNews(News news);

    boolean editNews(News news);

    News changeEditWebPage(News webPage);

    List<News> getAllNewsByTypeId(Integer id);

    List<TypeOfNews> getAllTypes();
}
