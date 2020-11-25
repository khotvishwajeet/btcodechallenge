package com.bt.curd.lightbulb.repository;

import com.bt.curd.lightbulb.model.LightBulb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightBulbRepository extends MongoRepository<LightBulb, Long>{

}
