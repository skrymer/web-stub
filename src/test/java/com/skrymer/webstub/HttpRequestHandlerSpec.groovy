package com.skrymer.webstub

import com.skrymer.webstub.domain.Stub
import com.skrymer.webstub.handler.HttpRequestHandler
import com.skrymer.webstub.handler.NoActiveScriptIsSetException
import com.skrymer.webstub.handler.StubNameResolver
import com.skrymer.webstub.scriptexecutor.ScriptExecutor
import com.skrymer.webstub.service.StubService
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class HttpRequestHandlerSpec extends Specification {
    def mockScriptExecutor
    def mockStubService
    def mockStubNameResolver
    def sut
    def request
    def response

    def setup() {
        mockScriptExecutor = Mock(ScriptExecutor)
        mockStubService = Mock(StubService)
        mockStubNameResolver = Mock(StubNameResolver)
        request = new MockHttpServletRequest()
        response = new MockHttpServletResponse()

        sut = new HttpRequestHandler(mockScriptExecutor, mockStubService, mockStubNameResolver)
    }

    def "handle incoming http request"() {
        given: "a http request with path /some/path?stub=mystub"
            request.setParameter("stub", "mystub")

        when: "handling a request"
            sut.handle(request, response)

        then: "execute the active script on the stub with name mystub"
            1 * mockStubNameResolver.resolve(_) >> "mystub"
            1 * mockStubService.findStubByName("mystub") >> stub()
            1 * mockScriptExecutor.execute(_, _, _)
    }

    def "throw NoActiveScriptIsSetException if no active script is set"() {
        given: "a http request with path /some/path?stub=mystub and mystub has no active script"
            request.setParameter("stub", "mystub")

        def stub = stub()
            stub.setActiveScript(null)

        when: "handling a request"
            sut.handle(request, response)

        then: "throw NoActiveScriptIsSetException"
            1 * mockStubNameResolver.resolve(_) >> "mystub"
            1 * mockStubService.findStubByName("mystub") >> stub
            thrown(NoActiveScriptIsSetException.class)
    }

    def stub() {
        def script = new com.skrymer.webstub.domain.Script(1, "scriptName", "some content")
        def stub = new Stub("mystub", [script])
        stub.setActiveScript(script)

        return stub
    }
}
