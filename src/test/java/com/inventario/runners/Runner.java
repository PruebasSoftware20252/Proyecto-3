package com.inventario.runners;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.*;
@Cucumber
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
        value = "com.inventario.steps,com.inventario.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report.html, json:target/cucumber.json")
//@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME,
  //      value = "@issue-1")
public class Runner {

}
