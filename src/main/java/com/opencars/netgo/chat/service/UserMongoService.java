package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.UserMongo;

import java.util.Optional;

public interface UserMongoService {

    UserMongo save(UserMongo userMongo);

    Optional<UserMongo> getOne(String id);

    Optional<UserMongo> getByIdIntranet(int id);

    boolean existsByIdIntranet(int idIntranet);
}
