package com.t2p.service;

import com.t2p.persistence.entity.News;
import com.t2p.persistence.entity.TypeOfNews;
import com.t2p.persistence.repository.NewsRepository;
import com.t2p.persistence.repository.TypeOfNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class NewsService implements INewsService {
    protected static final Logger logger
            = Logger.getLogger(NewsService.class.getName());



    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TypeOfNewRepository typeOfNewRepository;

    @Override
    public News getNewsById(Integer id) {
        return newsRepository.findById(id).get();
    }

    @Override
    public News getNewsByUrl(String url) {
        return newsRepository.findAllByUrl(url);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getAllNewsByType(TypeOfNews type) {
        return type.getNews();
    }

    @Override
    public boolean deleteNewsById(Integer id) {
        try {
            newsRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public News addNews(News news) {
           return newsRepository.save(news);
      }

    @Override
    public boolean editNews(News news) {
        try {
            newsRepository.save(news);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public News changeEditWebPage(News webPage) {
        return newsRepository.save(webPage);
    }

    @Override
    public List<News> getAllNewsByTypeId(Integer id) {
        return typeOfNewRepository.findById(id).get().getNews();
    }

    @Override
    public List<TypeOfNews> getAllTypes() {
        return typeOfNewRepository.findAll();
    }
}
