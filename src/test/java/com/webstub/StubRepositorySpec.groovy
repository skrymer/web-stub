package com.webstub

import com.webstub.domain.Stub
import com.webstub.repository.StubRepository
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

    def cleanup(){
        mongoTemplate.dropCollection(Stub.class)
    }

    def "save"(){
        given: "a new Stub"
            def newStub = stub()

        when: "saving it"
            sut.save(newStub)

        then: "the stub has been saved in mongo"
            def savedStub = mongoTemplate.findAll(Stub.class).get(0)

            savedStub.getName()          == newStub.getName()
            savedStub.getScripts()       == newStub.getScripts()
    }

    def "update"(){
        setup:
            mongoTemplate.save(stub())
            def Stub existingStub = mongoTemplate.findAll(Stub.class).get(0)

        when: "updating a stub"
            def newScript = new com.webstub.domain.Script(1, "updated", "updated path", "Updated content")
            existingStub.setScripts([newScript])

            sut.save(existingStub)

        then: "the stub has been updated in mongo"
            def Stub updatedStub = mongoTemplate.findAll(Stub.class).get(0)
            def updatedScript = updatedStub.getScripts().get(0)

            mongoTemplate.findAll(Stub.class).size() == 1
            updatedScript.getName()     ==  "updated"
            updatedScript.getPath()     ==  "updated path"
            updatedScript.getContent()  ==  "Updated content"
    }

    def "delete"(){
        setup:
            mongoTemplate.save(stub())
            def Stub existingStub = mongoTemplate.findAll(Stub.class).get(0)

        when: "Deleting a stub"
            sut.delete(existingStub)

        then: "the stub is deleted"
            mongoTemplate.findAll(Stub.class).size() == 0
    }

    def "find all stubs"(){
        setup:
            sut.save(stub())
            sut.save(stub())

        when: "Finding all stubs"
            def stubs = sut.findAll()

        then: "return all stubs"
            stubs.size() == 2
    }

    def stub(){
        def script1 = new com.webstub.domain.Script(1, "name", "path", "Some content")
        def script2 = new com.webstub.domain.Script(2, "name2", "path2", "Some content2")

        return new Stub("name", [script1, script2])
    }
}
