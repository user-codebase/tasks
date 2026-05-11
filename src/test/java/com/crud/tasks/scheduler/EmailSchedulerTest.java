package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldSendEmailWithSingularTask() {
        // Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("test@mail.com");

        // When
        emailScheduler.sendInformationEmail();

        // Then
        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService).send(mailCaptor.capture());

        Mail sentMail = mailCaptor.getValue();

        assertEquals("test@mail.com", sentMail.getMailTo());
        assertEquals("Tasks: Once a day email", sentMail.getSubject());
        assertTrue(sentMail.getMessage().contains("1 task"));
    }

    @Test
    void shouldSendEmailWithMultipleTasks() {
        // Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("test@mail.com");

        // When
        emailScheduler.sendInformationEmail();

        // Then
        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService).send(mailCaptor.capture());

        Mail sentMail = mailCaptor.getValue();

        assertEquals("test@mail.com", sentMail.getMailTo());
        assertEquals("Tasks: Once a day email", sentMail.getSubject());
        assertTrue(sentMail.getMessage().contains("5 tasks"));
    }
}