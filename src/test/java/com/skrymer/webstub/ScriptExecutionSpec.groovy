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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class ScriptExecutionSpec extends Specification {
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;
    @Autowired
    MongoTemplate mongo;

    def setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    def "Test execute active script for stub specified in request"() {
        given: "A stub with name awesomeStub exists with a active script set"
            createStub()

        when: "A http request with path /execute/more/path?stub=awesomeStub"
            def response = mockMvc.perform(get("/execute/more/path?stub=awesomeStub"))

        then: "Execute awesomeStubs active script"
            response.andExpect(content().string("Hello from awesomeStub!"))
    }

    def "Test execute script on demand"(){
        given: "a script encoded with Base64"
            def encodedScript = com.skrymer.webstub.domain.Script.encode("""out.print("Hello!")""")

        when: "A post http request with path /script/execution"
            def response = mockMvc.perform(post("/script/execution").content(encodedScript))

        then: "Execute awesomeStubs active script"
            response.andExpect(content().string("Hello!"))
    }

    def createStub() {
        def scriptContent = com.skrymer.webstub.domain.Script.encode("""out.print("Hello from awesomeStub!")""")
        def script = new com.skrymer.webstub.domain.Script(1, "activeScript", scriptContent)
        def stub = new Stub("awesomeStub", [script])
        stub.setActiveScript(script)

        mongo.insert(stub)
    }
}
