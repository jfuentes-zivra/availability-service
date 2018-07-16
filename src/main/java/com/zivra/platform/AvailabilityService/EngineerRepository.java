package com.zivra.platform.AvailabilityService;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface EngineerRepository extends MongoRepository<Engineer, String>{

    public Engineer findByFirstName(String firstName);
    public List<Engineer> findByLastName(String lastName);
    public List<Engineer> findAll();

}
