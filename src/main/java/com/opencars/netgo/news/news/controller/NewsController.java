package com.opencars.netgo.news.news.controller;

import com.opencars.netgo.news.news.entity.News;
import com.opencars.netgo.news.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/api")
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @MessageMapping("/noticias")
    @SendTo("/noticias/nueva")
    public ResponseEntity<?> receiptNew(News news) {
        newsService.save(news);
        return new ResponseEntity<>(newsService.save(news), HttpStatus.CREATED);
    }

    @MessageMapping("/noticias/eliminar")
    @SendTo("/noticias/eliminada")
    public ResponseEntity<?> deleteNews(News news) {
        Optional<News> newsOptional = newsService.getOne(news.getId());

        if (newsOptional.isPresent()) {
            newsService.deleteById(news.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró la noticia", HttpStatus.NOT_FOUND);
        }
    }

    @MessageMapping("/list")
    public void getList(){
        webSocket.convertAndSend("/noticias/list", newsService.obtainList());
    }
}
