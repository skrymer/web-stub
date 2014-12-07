package com.skrymer.webstub

import com.skrymer.webstub.scriptexecutor.CouldNotExecuteScriptException
import com.skrymer.webstub.scriptexecutor.GroovyScriptExecutor
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GroovyScriptExecutorSpec extends Specification {
    def sut = new GroovyScriptExecutor()
    def response = Mock(HttpServletResponse.class)
    def request = Mock(HttpServletRequest.class)
    def scriptContent = com.skrymer.webstub.domain.Script.encode("""
                                                                    request.getMethod()
                                                                    response.getStatus()
                                                                """)
    def "test execute script"() {
        given: "a script"
            def com.skrymer.webstub.domain.Script script = new com.skrymer.webstub.domain.Script(1, "name", scriptContent)

        when: "executing a script"
            sut.execute(script, request, response)

        then: "execute the script"
            1 * request.getMethod()
            1 * response.getOutputStream()
    }

    def "throw CouldNotExecuteScriptException if script could not be executed"() {
        given: "a faulty script"
        def com.skrymer.webstub.domain.Script script = new com.skrymer.webstub.domain.Script(1, "name", """sonni.unknownMethod()""")

        when: "executing a script"
        sut.execute(script, request, response)

        then: "throw CouldNotExecuteScriptException"
        thrown(CouldNotExecuteScriptException.class)
    }
}
