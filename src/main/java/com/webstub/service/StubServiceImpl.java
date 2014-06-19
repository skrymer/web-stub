package com.webstub.service;

import com.webstub.domain.Stub;
import com.webstub.repository.StubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StubServiceImpl implements StubService {
    private StubRepository stubRepository;

    @Autowired
    public StubServiceImpl(StubRepository stubRepository){
        this.stubRepository = stubRepository;
    }

    @Override
    public Stub createStub(Stub stub) {
        return stubRepository.save(stub);
    }

    @Override
    public Stub updateStub(Stub stub){
        return stubRepository.save(stub);
    }

    @Override
    public List<Stub> getAll() {
        return stubRepository.findAll();
    }

    @Override
    public void deleteStub(Stub stub) {
        stubRepository.delete(stub);
    }

    @Override
    public void setActiveScript(String stubName, String scriptName) {
			Stub stub = stubRepository.findByName(stubName);

			stub.setActiveScript(scriptName);

			stubRepository.save(stub);
    }
}
