package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfiguration.class)
class CoreConfigurationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void shouldCreateRestTemplateBean() {
        // Then
        assertNotNull(restTemplate);
    }
}