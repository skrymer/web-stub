package com.skrymer.webstub;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created with IntelliJ IDEA.
 * User: skrymer
 * Date: 29/11/14
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */

@ComponentScan(basePackages = {"com.skrymer.webstub.controller", "com.skrymer.webstub.service", "com.skrymer.webstub.handler", "com.skrymer.webstub.scriptexecutor", "com.skrymer.webstub.repository"})
@Import(FongoConfig.class)
@EnableAutoConfiguration
public class TestConfig {
}
