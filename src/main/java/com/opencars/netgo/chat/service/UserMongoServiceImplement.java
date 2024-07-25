package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.UserMongo;
import com.opencars.netgo.chat.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMongoServiceImplement implements UserMongoService{

    @Autowired
    UserMongoRepository userMongoRepository;

    @Override
    public UserMongo save(UserMongo userMongo) {
        return userMongoRepository.save(userMongo);
    }

    @Override
    public Optional<UserMongo> getOne(String id) {
        return userMongoRepository.findById(id);
    }

    @Override
    public Optional<UserMongo> getByIdIntranet(int idIntranet) {
        return userMongoRepository.findByIdIntranet(idIntranet);
    }

    @Override
    public boolean existsByIdIntranet(int idIntranet){
        return userMongoRepository.existsByIdIntranet(idIntranet);
    }
}
