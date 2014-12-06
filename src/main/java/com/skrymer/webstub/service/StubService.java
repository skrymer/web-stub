package com.skrymer.webstub.service;

import com.skrymer.webstub.domain.Stub;

import java.util.List;


public interface StubService {
  Stub createStub(Stub stub);

  Stub updateStub(Stub stub);

  List<Stub> getAll();

  void deleteStubByName(String stubName);

  Stub findStubByName(String name);
}
