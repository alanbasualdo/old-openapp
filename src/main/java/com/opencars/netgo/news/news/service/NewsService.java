package com.opencars.netgo.news.news.service;

import com.opencars.netgo.news.news.entity.News;
import com.opencars.netgo.news.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

   @Autowired
    NewsRepository newsRepository;

    public News save(News news) {
        return newsRepository.save(news);
    }

    public Optional<News> getOne(String id){
        return newsRepository.findById(id);
    }

    public void deleteById(String id){
        newsRepository.deleteById(id);
    }

    public List<News> obtainList() {
        return newsRepository.findAll();
    }
}
