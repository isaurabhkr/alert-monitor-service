package com.alerts.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alerts.model.Event;
import com.alerts.model.EventDTO;
import com.alerts.model.EventType;
import com.alerts.service.EventService;

@ExtendWith(MockitoExtension.class)
public class AlertControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private AlertController alertController;

    @Test
    public void testCreateEvent() throws Exception {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setClient("client1");
        eventDTO.setEventType("PAYMENT_EXCEPTION");

        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();

        mockMvc.perform(post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"client\":\"client1\",\"eventType\":\"PAYMENT_EXCEPTION\"}"))
                .andExpect(status().isNoContent());

        verify(eventService, times(1)).createEvent(eq("client1"), eq("PAYMENT_EXCEPTION"));
    }

    @Test
    public void testGetEvents() throws Exception {
        List<Event> events = Arrays.asList(new Event("client1", EventType.PAYMENT_EXCEPTION), new Event("client1", EventType.PAYMENT_EXCEPTION));

        when(eventService.getEvents("client1", "PAYMENT_EXCEPTION")).thenReturn(events);

        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();

        mockMvc.perform(get("/api/events/client1/PAYMENT_EXCEPTION"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(events.size()));

        verify(eventService, times(1)).getEvents("client1", "PAYMENT_EXCEPTION");
    }

    @Test
    public void testClearEvents() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();

        mockMvc.perform(delete("/api/events/client1/PAYMENT_EXCEPTION"))
                .andExpect(status().isNoContent());

        verify(eventService, times(1)).clearEvents("client1", "PAYMENT_EXCEPTION");
    }
}
