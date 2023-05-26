package com.roy.webfluxdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@AutoConfigureWebTestClient
class IndexControllerTest {

    private final WebTestClient client;
    private final ServerProperties serverProperties;

    @Autowired
    public IndexControllerTest(WebTestClient client, ServerProperties serverProperties) {
        this.client = client;
        this.serverProperties = serverProperties;
    }

    @Test
    void generateToNumbersError() {
        int[] expected = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        client.get()
                .uri("http://localhost:" + serverProperties.getPort() + "/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(int[].class)
                .consumeWith(response -> assertArrayEquals(expected, response.getResponseBody()));
    }

    @Test
    void pathNotFoundException() {
        client.get()
                .uri("http://localhost:" + serverProperties.getPort() + "/invalid-path")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}