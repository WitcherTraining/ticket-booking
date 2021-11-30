package com.epam.ticket.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Objects;

class CommonExceptionHandlerControllerTest {
    @Test
    void testDoResolveException() {
        //GIVEN
        CommonExceptionHandlerController commonExceptionHandlerController = new CommonExceptionHandlerController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //THEN_THROW
        assertTrue(Objects.requireNonNull(commonExceptionHandlerController
                        .doResolveException(request, response, "Handler", new Exception("An error occurred"))).isReference());
    }
}

