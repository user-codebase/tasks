package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBadgesDto {

    private int votes;
    private TrelloAttachmentsByTypeDto attachmentsByType;
}