package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TrelloConfigTest {

    @Test
    void shouldSetAndGetFields() throws Exception {
        TrelloConfig config = new TrelloConfig();

        setField(config, "trelloApiEndpoint", "https://test-api.com");
        setField(config, "trelloAppKey", "test-key");
        setField(config, "trelloToken", "test-token");
        setField(config, "trelloUser", "test-user");

        assertEquals("https://test-api.com", config.getTrelloApiEndpoint());
        assertEquals("test-key", config.getTrelloAppKey());
        assertEquals("test-token", config.getTrelloToken());
        assertEquals("test-user", config.getTrelloUser());
    }

    private void setField(Object target, String fieldName, String value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}