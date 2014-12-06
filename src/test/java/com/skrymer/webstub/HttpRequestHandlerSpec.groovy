package com.skrymer.webstub

import com.skrymer.webstub.domain.Stub
import com.skrymer.webstub.handler.HttpRequestHandler
import com.skrymer.webstub.scriptexecutor.ScriptExecutor
import com.skrymer.webstub.service.StubService
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

/**
 * Created by skrymer on 20/06/14.
 */
class HttpRequestHandlerSpec extends Specification {
    def mockScriptExecutor
    def mockStubService
    def sut

    def setup() {
        mockScriptExecutor = Mock(ScriptExecutor)
        mockStubService = Mock(StubService)
        sut = new HttpRequestHandler(mockScriptExecutor, mockStubService)
    }

    def "handle incoming http request"() {
        given: "a http request with path /some/path?stub=mystub"
        def request = new MockHttpServletRequest()
        def response = new MockHttpServletResponse()

        request.setParameter("stub", "mystub")

        when: "handling a request"
        sut.handle(request, response)

        then: "execute the active script on the stub with name mystub"
        1 * mockStubService.findStubByName("mystub") >> stub()
        1 * mockScriptExecutor.execute(_, _, _)
    }

    //TODO ScriptExecutor should throw exception script could not be executed

    def stub() {
        def script = new com.skrymer.webstub.domain.Script(1, "scriptName", "some content")
        def stub = new Stub("mystub", [script])
        stub.setActiveScript(script)

        return stub
    }
}
