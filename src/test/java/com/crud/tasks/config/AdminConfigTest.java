package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AdminConfig.class)
@TestPropertySource(properties = {
        "admin.mail=test@mail.com"
})
class AdminConfigTest {

    @Autowired
    private AdminConfig adminConfig;

    @Test
    void shouldLoadAdminMailFromProperties() {
        assertEquals("test@mail.com", adminConfig.getAdminMail());
    }
}