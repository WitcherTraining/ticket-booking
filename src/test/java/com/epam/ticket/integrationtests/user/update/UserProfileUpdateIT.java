package com.epam.ticket.integrationtests.user.update;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        glue = "com.epam.ticket.integrationtests.user.update",
        features = "src/test/java/com/epam/ticket/update_user.feature",
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class UserProfileUpdateIT {
}
