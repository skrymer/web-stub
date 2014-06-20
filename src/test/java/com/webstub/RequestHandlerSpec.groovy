package com.webstub

import com.webstub.handler.RestRequestHandler
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

/**
 * Created by skrymer on 20/06/14.
 */
class RequestHandlerSpec extends Specification {

    def "handle incoming rest request"(){
        given: "a rest request with path /mystub/some/path"
            def sut = new RestRequestHandler()
            def request = new MockHttpServletRequest()
            request.setContextPath("/mystub/some/path")

        when: "handling a rest request"
            sut.handle(request)

        then: "execute the active script on the stub with name mystub"
            //Call StubService to get stub with name mystub
            //Call mystub.getActiveScript()
            //Call ScriptExecutor with the returned script
    }
}
