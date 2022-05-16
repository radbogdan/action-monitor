package com.betvictor.actionmonitor.api;

import com.betvictor.actionmonitor.util.Constants;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthCheckControllerTest extends AbstractBaseITest {
    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void health_Return_200_Test() {
        this.mockMvc.perform(get(Constants.STATUS_URL))
            .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void applicationVersion() {
        this.mockMvc.perform(get(Constants.VERSION_URL))
            .andExpect(status().isOk());
    }
}