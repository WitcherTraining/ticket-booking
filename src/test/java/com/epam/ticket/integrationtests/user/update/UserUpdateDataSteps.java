package com.epam.ticket.integrationtests.user.update;

import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.UserImpl;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserUpdateDataSteps {

    private final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

    @When("^I trying to change my name to \"([^\"]*)\"$")
    public void iTryingToChangeMyNameTo(String name) {

        User user = new UserImpl();
        user.setId(1L);
        user.setName(name);
        user.setEmail("lera@gmail.com");

        bookingFacade.updateUser(user);
    }

    @Then("^I see updated user profile with new name \"([^\"]*)\"$")
    public void iSeeNewNameInMyProfile(String updatedName) {
        final List<User> usersByName = bookingFacade.getUsersByName(updatedName, 1, 1);
        final User first = usersByName.stream().filter(user -> user.getName().equals(updatedName)).findFirst().orElseThrow(EntityNotFoundException::new);
        Assert.assertEquals(updatedName, first.getName());
    }

}
