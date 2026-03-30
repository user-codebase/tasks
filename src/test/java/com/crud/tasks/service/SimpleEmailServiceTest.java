package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
//        Mail mail = new Mail("test@test.com", "Test", "Test Message");
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test Message")
                .build();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(mail.getMailTo());
//        mailMessage.setSubject(mail.getSubject());
//        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();

        assertEquals("test@test.com", sentMessage.getTo()[0]);
        assertEquals("Test", sentMessage.getSubject());
        assertEquals("Test Message", sentMessage.getText());
        assertNull(sentMessage.getCc());
    }

    @Test
    public void shouldSendEmailWithCc() {
        // Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test Message")
                .toCc("test2@test.com")
                .build();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // When
        simpleEmailService.send(mail);

        // Then
        verify(javaMailSender).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();

        assertEquals("test@test.com", sentMessage.getTo()[0]);
        assertEquals("Test", sentMessage.getSubject());
        assertEquals("Test Message", sentMessage.getText());
        assertEquals("test2@test.com", sentMessage.getCc()[0]);
    }

}