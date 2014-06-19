package com.webstub.controller;


import com.webstub.domain.Stub;
import com.webstub.service.StubService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StubController {
    private static final Log LOG = LogFactory.getLog(StubController.class);

    @Autowired
    private StubService stubService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Stub create(@RequestBody Stub stub){
        LOG.info("Received stub: " + stub);

        return stubService.createStub(stub);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void update(@RequestBody Stub stub){
        stubService.updateStub(stub);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void delete(@RequestBody Stub stub){
        stubService.deleteStub(stub);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Stub> getAll(){
        return stubService.getAll();
    }
}
