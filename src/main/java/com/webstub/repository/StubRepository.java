package com.webstub.repository;

import com.webstub.domain.Stub;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StubRepository extends MongoRepository<Stub, String> {

	Stub findByName(String name);
}
