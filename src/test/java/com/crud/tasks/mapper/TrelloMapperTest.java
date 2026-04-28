package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    @Test
    void shouldMapToBoards() {
        // given
        List<TrelloListDto> lists = List.of(new TrelloListDto("1", "List 1", false));
        List<TrelloBoardDto> boardDtos = List.of(new TrelloBoardDto("1", "Board 1", lists));
        TrelloMapper mapper = new TrelloMapper();

        // when
        List<TrelloBoard> result = mapper.mapToBoards(boardDtos);

        // then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Board 1", result.get(0).getName());
        assertEquals(1, result.get(0).getLists().size());
    }

    @Test
    void shouldMapToBoardsDto() {
        // given
        List<TrelloList> lists = List.of(new TrelloList("1", "List 1", false));
        List<TrelloBoard> boards = List.of(new TrelloBoard("1", "Board 1", lists));
        TrelloMapper mapper = new TrelloMapper();

        // when
        List<TrelloBoardDto> result = mapper.mapToBoardsDto(boards);

        // then
        assertEquals(1, result.size());
        assertEquals("Board 1", result.get(0).getName());
        assertEquals(1, result.get(0).getLists().size());
    }

    @Test
    void shouldMapToCard() {
        // given
        TrelloCardDto dto = new TrelloCardDto("name", "desc", "top", "123");
        TrelloMapper mapper = new TrelloMapper();

        // when
        TrelloCard card = mapper.mapToCard(dto);

        // then
        assertEquals("name", card.getName());
        assertEquals("desc", card.getDescription());
        assertEquals("top", card.getPos());
        assertEquals("123", card.getListId());
    }

    @Test
    void shouldMapToCardDto() {
        // given
        TrelloCard card = new TrelloCard("name", "desc", "top", "123");
        TrelloMapper mapper = new TrelloMapper();

        // when
        TrelloCardDto dto = mapper.mapToCardDto(card);

        // then
        assertEquals("name", dto.getName());
        assertEquals("desc", dto.getDescription());
    }
}