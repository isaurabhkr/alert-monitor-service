package com.alerts.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alerts.model.DispatchType;

class AlertingServiceTest {
    private AlertingService alertingService;

    @Mock
    private DispatchConsole dispatchConsole;

    @Mock
    private DispatchEmail dispatchEmail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alertingService = new AlertingService();
        alertingService.dispatchConsole = dispatchConsole;
        alertingService.dispatchEmail = dispatchEmail;
    }

    @Test
    void testDispatch_Email() {
        String message = "Test message";
        String subject = "Test subject";
        DispatchType dispatchType = DispatchType.EMAIL;

        alertingService.dispatch(dispatchType, message, subject);

        verify(dispatchEmail).dispatch(message, subject);
        verifyNoMoreInteractions(dispatchConsole);
    }

    @Test
    void testDispatch_Console() {
        String message = "Test message";
        String subject = "Test subject";
        DispatchType dispatchType = DispatchType.CONSOLE;

        alertingService.dispatch(dispatchType, message, subject);

        verify(dispatchConsole).dispatch(message, subject);
        verifyNoMoreInteractions(dispatchEmail);
    }

}
