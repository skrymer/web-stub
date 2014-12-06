package com.skrymer.webstub

import com.mongodb.Mongo
import com.skrymer.webstub.domain.Stub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Specification for StubController
 */
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class StubControllerSpec extends Specification {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    MongoTemplate mongo;

    @Autowired
    Mongo fongo;

    def setup() {
        fongo.dropDatabase("fongo")
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "Test create new stub"() {
        given: "A new stub"
        def stubAsJson = """{"name":"awesome","scripts":[{"id":1,"name":"script1"},{"id":2,"name":"script2"}]}"""

        when: "Creating new stub"
        def response = mockMvc.perform(post("/api/").contentType(MediaType.APPLICATION_JSON).content(stubAsJson))

        then: "The new stub is created"
        response.andExpect(status().isOk())
                .andExpect(jsonPath("\$.name").value("awesome"))

        Stub createdStub = mongo.findAll(Stub.class).get(0)
        createdStub.name == "awesome"
        createdStub.scripts.size() == 2
    }

    def "Test delete stub"() {
        given: "A stub to delete"
        mongo.insert(stub())
        mongo.findAll(Stub.class).size() == 1

        when: "Deleting stub"
        def response = mockMvc.perform(delete("/api/awesome"))

        then: "The stub is deleted"
        response.andExpect(status().isOk())
        mongo.findAll(Stub.class).size() == 0
    }

    def stub() {
        def script1 = new com.skrymer.webstub.domain.Script(1, "script1", "Some content")
        def script2 = new com.skrymer.webstub.domain.Script(2, "script2", "Some content2")

        return new Stub("awesome", [script1, script2])
    }
}
