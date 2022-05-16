package com.betvictor.actionmonitor.api;

import com.betvictor.actionmonitor.storage.MongoMessageRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractBaseITest {
    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    MongoMessageRepository mongoMessageRepository;

    @DynamicPropertySource
    public static void overrideProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @SneakyThrows
    @BeforeAll
    public static void setup() {
        mongoDBContainer.start();

    }

    @AfterAll
    public static void clean() {
        mongoDBContainer.stop();
    }

    @Test
    void containerStartsAndPublicPortIsAvailable() {
        assertThatPortIsAvailable(mongoDBContainer);
    }

    private void assertThatPortIsAvailable(final MongoDBContainer container) {
        try {
            new Socket(container.getContainerIpAddress(), container.getFirstMappedPort());
        } catch (IOException e) {
            throw new AssertionError("The expected port " + container.getFirstMappedPort() + " is not available!");
        }
    }
}
