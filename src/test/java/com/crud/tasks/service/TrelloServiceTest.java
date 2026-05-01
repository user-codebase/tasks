package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldCreateCardAndSendEmail() {
        // Given
        TrelloCardDto inputDto = new TrelloCardDto(
                "Test card", "desc", "top", "list1"
        );

        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto(
                "1", "Test card", "url", null
        );

        when(trelloClient.createNewCard(inputDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@mail.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(inputDto);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getId());

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(emailService).send(mailCaptor.capture());

        Mail sentMail = mailCaptor.getValue();

        assertEquals("admin@mail.com", sentMail.getMailTo());
        assertEquals("Tasks: New Trello card", sentMail.getSubject());
        assertTrue(sentMail.getMessage().contains("Test card"));
    }

    @Test
    void shouldNotSendEmailWhenCardIsNull() {
        // Given
        TrelloCardDto inputDto = new TrelloCardDto(
                "Test card", "desc", "top", "list1"
        );

        when(trelloClient.createNewCard(inputDto)).thenReturn(null);

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(inputDto);

        // Then
        assertNull(result);
        verify(emailService, never()).send(any());
    }

    @Test
    void shouldCallTrelloClient() {
        TrelloCardDto inputDto = new TrelloCardDto(
                "Test card", "desc", "top", "list1"
        );

        trelloService.createTrelloCard(inputDto);

        verify(trelloClient).createNewCard(inputDto);
    }

}