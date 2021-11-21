package com.epam.ticket.integrationtests.user.workflow;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        glue = "com.epam.ticket.integrationtests.user.workflow",
        features = "src/test/java/com/epam/ticket/user_workflow.feature",
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class UserStandardWorkflowIT {
}
