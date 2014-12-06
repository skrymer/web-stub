package com.skrymer.webstub

import com.skrymer.webstub.domain.Stub
import com.skrymer.webstub.repository.StubRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = FongoConfig.class)
class StubRepositorySpec extends Specification {
    @Autowired
    StubRepository sut

    @Autowired
    MongoTemplate mongoTemplate

    def cleanup() {
        mongoTemplate.dropCollection(Stub.class)
    }

    def "save"() {
        given: "a new Stub"
        def newStub = stub()

        when: "saving it"
        sut.save(newStub)

        then: "the stub has been saved in mongo"
        def savedStub = mongoTemplate.findAll(Stub.class).get(0)

        savedStub.getName() == newStub.getName()
        savedStub.getScripts() == newStub.getScripts()
    }

    def "update"() {
        setup:
        mongoTemplate.save(stub())
        def Stub existingStub = mongoTemplate.findAll(Stub.class).get(0)

        when: "updating a stub"
        def newScript = new com.skrymer.webstub.domain.Script(1, "updated", "Updated content")
        existingStub.setScripts([newScript])

        sut.save(existingStub)

        then: "the stub has been updated in mongo"
        def Stub updatedStub = mongoTemplate.findAll(Stub.class).get(0)
        def updatedScript = updatedStub.getScripts().get(0)

        mongoTemplate.findAll(Stub.class).size() == 1
        updatedScript.getName() == "updated"
        updatedScript.getContent() == "Updated content"
    }

    def "set active script"() {
        setup:
        mongoTemplate.save(stub())
        def existingStub = mongoTemplate.findAll(Stub.class).get(0)

        when: "setting a stubs active script"
        def activeScript = existingStub.getScript("script1")
        existingStub.setActiveScript(activeScript)
        sut.save(existingStub)

        then: "the active script has been set"
        mongoTemplate.findAll(Stub.class).get(0).getActiveScript().getName() == activeScript.getName()
    }

    def "delete stub by name"() {
        setup:
        mongoTemplate.save(stub())
        def Stub existingStub = mongoTemplate.findAll(Stub.class).get(0)

        when: "Deleting a stub"
        sut.deleteStubByName(existingStub.getName())

        then: "the stub is deleted"
        mongoTemplate.findAll(Stub.class).size() == 0
    }

    def "find all stubs"() {
        setup:
        mongoTemplate.save(stub())
        mongoTemplate.save(stub())

        when: "Finding all stubs"
        def stubs = sut.findAll()

        then: "return all stubs"
        stubs.size() == 2
    }

    def "find by name"() {
        def name = "name"

        setup:
        mongoTemplate.save(stub())

        when: "find a stub by name"
        def actualStub = sut.findByName(name)

        then: "the stub with the given name is returned"
        actualStub.getName() == name
    }

// Helper functions

    def stub() {
        def script1 = new com.skrymer.webstub.domain.Script(1, "script1", "Some content")
        def script2 = new com.skrymer.webstub.domain.Script(2, "script2", "Some content2")

        return new Stub("name", [script1, script2])
    }
}
