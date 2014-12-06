package com.skrymer.webstub

import com.skrymer.webstub.domain.Stub
import com.skrymer.webstub.repository.StubRepository
import com.skrymer.webstub.service.StubNotFoundException
import com.skrymer.webstub.service.StubService
import com.skrymer.webstub.service.StubServiceImpl
import spock.lang.Specification

class StubServiceSpec extends Specification {
    def StubService sut;
    def StubRepository mockStubRepository

    def setup() {
        mockStubRepository = Mock(StubRepository)
        sut = new StubServiceImpl(mockStubRepository)
    }

    def "Test find by name"() {
        when: "Finding a stub with name awesome"
        def stub = sut.findStubByName("awesome")

        then: "Return the stub with name awesome"
        1 * mockStubRepository.findByName("awesome") >> createStub()
        stub.name == "awesome"
    }

    def "Throw StubNotFoundException if no stub is found"() {
        when: "No stub is found with name awesome"
        1 * mockStubRepository.findByName("awesome") >> null
        sut.findStubByName("awesome")

        then: "Throw StubNotFoundException"
        thrown(StubNotFoundException)
    }

    def createStub() {
        def script = new com.skrymer.webstub.domain.Script(1, "name", "content")
        def stub = new Stub("awesome", [script])

        return stub
    }
}
