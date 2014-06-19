package com.webstub.service;

import com.webstub.domain.Stub;

import java.util.List;


public interface StubService {
    Stub createStub(Stub stub);

    Stub updateStub(Stub stub);

    List<Stub> getAll();

    void deleteStub(Stub stub);
}
