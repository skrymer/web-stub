package com.skrymer.webstub

import com.skrymer.webstub.scriptexecutor.GroovyScriptExecutor
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GroovyScriptExecutorSpec extends Specification {
    def scriptContent = """
                            request.getMethod()
                            response.getStatus()
                        """

    def sut = new GroovyScriptExecutor()

    def "test execute script"() {
        given: "a script, HttpServletRequest and a HttpServletResponse"
        def com.skrymer.webstub.domain.Script script = new com.skrymer.webstub.domain.Script(1, "name", scriptContent)
        def response = Mock(HttpServletResponse.class)
        def request = Mock(HttpServletRequest.class)

        when: "executing a script"
        sut.execute(script, request, response)

        then: "execute the script"
        1 * request.getMethod()
        1 * response.getOutputStream()
    }
}
