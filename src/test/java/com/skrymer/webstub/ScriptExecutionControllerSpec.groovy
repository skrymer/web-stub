package com.skrymer.webstub

import com.skrymer.webstub.domain.Stub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

/**
 * Created with IntelliJ IDEA.
 * User: skrymer
 * Date: 30/11/14
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class ScriptExecutionControllerSpec extends Specification {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    MongoTemplate mongo;

    def setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    def "Test execute active script for a request"() {
        given: "A stub with name awesomeStub exists with a active script set"
        createStub()

        when: "A http request with path /execute/more/path?stub=awesomeStub"
        def response = mockMvc.perform(get("/execute/more/path?stub=awesomeStub"))

        then: "Execute awesomeStubs active script"
        response.andExpect(content().string("Hello from awesomeStub!"))
    }

    def createStub() {
        def script = new com.skrymer.webstub.domain.Script(1, "activeScript", """out.print("Hello from awesomeStub!")""")
        def stub = new Stub("awesomeStub", [script])
        stub.setActiveScript(script)

        mongo.insert(stub)
    }
}
