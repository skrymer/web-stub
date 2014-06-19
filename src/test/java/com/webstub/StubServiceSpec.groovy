package com.webstub

import com.webstub.repository.StubRepository
import com.webstub.service.StubServiceImpl
import spock.lang.Specification

class StubServiceSpec extends Specification {

    def "set active script for stub"() {

        def mockStubRepository = Mock(StubRepository)
        StubServiceImpl sut = new StubServiceImpl(mockStubRepository)

        given: "a script name and a stub name"
            def scriptName = "scriptName"
            def stubName   = "stubName"

        when: "setting stubs active script"
            sut.setActiveScript(scriptName, stubName)

        then: "the stub has been saved with the given active script"
            1 * mockStubRepository.save( { it.getActiveScript().getName() == scriptName } )
    }
}
