package com.opencars.netgo.news.news.repository;

import com.opencars.netgo.news.news.entity.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {
}
