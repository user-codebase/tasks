package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloValidatorTest {

    private final TrelloValidator validator = new TrelloValidator();

    @Test
    void shouldFilterOutTestBoards() {
        // given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", List.of()),
                new TrelloBoard("2", "Kodilla", List.of()),
                new TrelloBoard("3", "Java", List.of())
        );

        // when
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        // then
        assertEquals(2, result.size());
    }
}