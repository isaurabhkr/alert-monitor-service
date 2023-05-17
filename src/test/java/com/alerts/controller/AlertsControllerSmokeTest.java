package com.alerts.controller;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AlertsControllerSmokeTest {
    
    @InjectMocks
    private AlertController controller;

    @Test
    public void controllerTest() {        
		assertThat(controller).isNotNull();
    }
    
}
