package com.skrymer.webstub.repository;

import com.skrymer.webstub.domain.Stub;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StubRepository extends MongoRepository<Stub, String> {

  Stub findByName(String name);

  Long deleteStubByName(String name);
}
