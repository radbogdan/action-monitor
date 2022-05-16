package com.betvictor.actionmonitor.api;

import com.betvictor.actionmonitor.api.model.MessageDto;
import com.betvictor.actionmonitor.storage.model.StoredMessage;
import com.betvictor.actionmonitor.util.Constants;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.betvictor.actionmonitor.util.Constants.ID_URL_VARIABLE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MessageControllerTest extends AbstractBaseITest {
    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void addMessage_Return_201_Test() {
        this.mockMvc.perform(post(Constants.MESSAGE_URL).param("message", "Test message"))
            .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void addMessageWhenNoParamIsSent_Return_400_Test() {
        this.mockMvc.perform(post(Constants.MESSAGE_URL))
            .andExpect(status().isBadRequest());
    }


    @SneakyThrows
    @Test
    void addMessageWhenEmpty_Return_500_Test() {
        this.mockMvc.perform(post(Constants.MESSAGE_URL).param("message", ""))
            .andExpect(status().isInternalServerError());
    }

    @SneakyThrows
    @Test
    void findMessageById_Return_200_Test() {
        final String id = setupData();
        this.mockMvc.perform(get(Constants.MESSAGE_URL + ID_URL_VARIABLE, id))
            .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message", is("Test message")));
    }

    @SneakyThrows
    @Test
    void findMessageById_Return_404_Test() {
        this.mockMvc.perform(get(Constants.MESSAGE_URL + ID_URL_VARIABLE, "fake").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void updateMessage_Return_200_Test() {
        final String id = setupData();
        final String updatedMessage = "Updated";
        final ObjectMapper mapper = new ObjectMapper();
        final MessageDto messageDto = new MessageDto();
        messageDto.setId(id);
        messageDto.setMessage(updatedMessage);
        mockMvc.perform(put(Constants.MESSAGE_URL + ID_URL_VARIABLE, id).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(messageDto))).andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is(updatedMessage)));
    }

    @SneakyThrows
    @Test
    void deleteMessageById_Return_204_Test() {
        final String id = setupData();
        this.mockMvc.perform(delete(Constants.MESSAGE_URL + ID_URL_VARIABLE, id).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    void findAllMessages_Return_200_Test() {
        final String id = setupData();
        this.mockMvc.perform(get(Constants.MESSAGE_URL))
            .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @SneakyThrows
    @Test
    void findAllMessages_Return_NoEntry_Test() {
        mongoMessageRepository.deleteAll();
        this.mockMvc.perform(get(Constants.MESSAGE_URL))
            .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    private String setupData() {
        mongoMessageRepository.deleteAll();
        final String id = mongoMessageRepository.save(new StoredMessage().setMessage("Test message")).getId();
        return id;
    }
}