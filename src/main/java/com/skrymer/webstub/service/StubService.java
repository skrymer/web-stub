package com.skrymer.webstub.service;

import com.skrymer.webstub.domain.Stub;

import java.util.List;


public interface StubService {
  Stub createStub(Stub stub);

  Stub updateStub(Stub stub);

  void deleteStubById(String id);

  List<Stub> getAll();

  Stub findStubByName(String name);
}
