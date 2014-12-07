package com.skrymer.webstub.controller;


import com.skrymer.webstub.domain.Stub;
import com.skrymer.webstub.handler.HttRequestContext;
import com.skrymer.webstub.handler.RequestHandler;
import com.skrymer.webstub.service.StubService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping()
public class StubController {
  private static final Log LOG = LogFactory.getLog(StubController.class);

  private StubService stubService;
  private RequestHandler requestHandler;

  @Autowired
  public StubController(StubService stubService, RequestHandler requestHandler){
    this.stubService = stubService;
    this.requestHandler = requestHandler;

  }

  @RequestMapping(value = "/api/", method = RequestMethod.POST)
  public Stub create(@RequestBody Stub stub) {
    LOG.info("Creating stub: " + stub);

    return stubService.createStub(stub);
  }

  @RequestMapping(value = "/api/", method = RequestMethod.PUT)
  public void update(@RequestBody Stub stub) {
    LOG.info("Updating stub: " + stub);

    stubService.updateStub(stub);
  }

  @RequestMapping(value = "/api/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String id) {
    LOG.info("Deleting stub: " + id);

    stubService.deleteStubById(id);
  }

  @RequestMapping(value = "/api/", method = RequestMethod.GET)
  public List<Stub> getAll() {
    return stubService.getAll();
  }

  @RequestMapping(value = "/execute/**", method = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
  public void executeActiveScript(HttpServletRequest request, HttpServletResponse response) {
    requestHandler.handle(new HttRequestContext(request, response));
  }
}
