package com.skrymer.webstub.service;

import com.skrymer.webstub.domain.Stub;

import java.util.List;


public interface StubService {
  Stub createStub(Stub stub);

  Stub updateStub(Stub stub);

  List<Stub> getAll();

  void deleteStub(Stub stub);

  Stub findStubByName(String name);
}
