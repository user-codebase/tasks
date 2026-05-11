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
        assertTrue(result.stream().noneMatch(b -> b.getName().contains("test")));
    }

    @Test
    void shouldReturnOnlyNonTestBoards() {
        // given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", List.of()),
                new TrelloBoard("2", "normal", List.of())
        );

        // when
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        // then
        assertEquals(1, result.size());
        assertEquals("normal", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenAllBoardsAreTest() {
        // given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", List.of()),
                new TrelloBoard("2", "test", List.of())
        );

        // when
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        // then
        assertTrue(result.isEmpty());
    }

}