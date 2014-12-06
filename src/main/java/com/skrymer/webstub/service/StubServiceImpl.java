package com.skrymer.webstub.service;

import com.skrymer.webstub.domain.Stub;
import com.skrymer.webstub.repository.StubRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StubServiceImpl implements StubService {
  private static final Log LOG = LogFactory.getLog(StubServiceImpl.class);

  private StubRepository stubRepository;

  @Autowired
  public StubServiceImpl(StubRepository stubRepository) {
    this.stubRepository = stubRepository;
  }

  @Override
  //TODO throw exception if stub already exists
  public Stub createStub(Stub stub) {
    return stubRepository.save(stub);
  }

  @Override
  public Stub updateStub(Stub stub) {
    return stubRepository.save(stub);
  }

  @Override
  public List<Stub> getAll() {
    return stubRepository.findAll();
  }

  @Override
  public void deleteStubByName(String stubName) {
    stubRepository.deleteStubByName(stubName);
  }

  @Override
  public Stub findStubByName(String name) {
    Stub stub = stubRepository.findByName(name);

    if (stub == null) {
      LOG.error("Could not find stub with name " + name);

      throw new StubNotFoundException("Could not find stub with name " + name);
    }

    return stub;
  }
}
