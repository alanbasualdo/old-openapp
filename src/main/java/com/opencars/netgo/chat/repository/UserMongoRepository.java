package com.opencars.netgo.chat.repository;

import com.opencars.netgo.chat.entity.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    boolean existsByIdIntranet(int idIntranet);

    Optional<UserMongo> findByIdIntranet(int idIntranet);
}
