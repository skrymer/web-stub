package com.webstub

import com.webstub.domain.Stub
import com.webstub.repository.StubRepository
import com.webstub.service.StubServiceImpl
import spock.lang.Specification

class StubServiceSpec extends Specification {

    def "set stubs active script"() {

        def mockStubRepository = Mock(StubRepository)
        StubServiceImpl sut = new StubServiceImpl(mockStubRepository)

        given: "a script and stub name"
            def scriptName = "script1"
            def stubName   = "stubName"

        when: "setting stubs active script"
            sut.setActiveScript(stubName, scriptName)

        then: "the active script has been saved"
            1 * mockStubRepository.findByName(stubName) >> stub()
            1 * mockStubRepository.save( { it.getActiveScript().getName() == scriptName } )
    }

//    def "if there are no script with the given name then throw NoScriptFoundException"(){
//        setup:
//            def mockStubRepository = Mock(StubRepository)
//            StubServiceImpl sut = new StubServiceImpl(mockStubRepository)
//            def noneExistingScript = "noneExisting"
//
//        when: "setting a active script that does not exist"
//            sut.setActiveScript()
//
//        then: "throw NoScriptFoundException"
//    }
//
//    def "if a stub with the given name does not exist then throw NoStubFoundException"(){
//        setup:
//        def mockStubRepository = Mock(StubRepository)
//        StubServiceImpl sut = new StubServiceImpl(mockStubRepository)
//        def noneExistingScript = "noneExisting"
//
//        when: "setting a active script that does not exist"
//        sut.setActiveScript()
//
//        then: "throw NoScriptFoundException"
//    }

    def stub(){
        def script1 = new com.webstub.domain.Script(1, "script1", "path", "Some content")
        def script2 = new com.webstub.domain.Script(2, "script2", "path2", "Some content2")

        return new Stub("name", [script1, script2])
    }
}
