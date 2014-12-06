package com.skrymer.webstub.controller;


import com.skrymer.webstub.domain.Stub;
import com.skrymer.webstub.service.StubService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StubController {
  private static final Log LOG = LogFactory.getLog(StubController.class);

  @Autowired
  private StubService stubService;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public Stub create(@RequestBody Stub stub) {
    LOG.info("Creating stub: " + stub);

    return stubService.createStub(stub);
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public void update(@RequestBody Stub stub) {
    LOG.info("Updating stub: " + stub);

    stubService.updateStub(stub);
  }

  @RequestMapping(value = "/{stubname}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String stubname) {
    LOG.info("Deleting stub: " + stubname);

    stubService.deleteStubByName(stubname);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Stub> getAll() {
    return stubService.getAll();
  }
}
