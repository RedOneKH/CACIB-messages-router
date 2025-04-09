package com.redone.messages_router.dto;

import lombok.Builder;
import lombok.Data;

import com.redone.messages_router.model.Partner;

@Data
@Builder
public class PartnerDTO {
    private Long id;
    private String alias;
    private String type;
    private Partner.Direction direction;
    private String application;
    private Partner.ProcessedFlowType processedFlowType;
    private String description;

}
