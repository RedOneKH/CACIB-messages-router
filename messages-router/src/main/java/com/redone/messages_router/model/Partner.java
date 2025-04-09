package com.redone.messages_router.model;

import com.redone.messages_router.dto.PartnerDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private Direction direction;

    @Column(name = "application")
    private String application;

    @Enumerated(EnumType.STRING)
    @Column(name = "processed_flow_type")
    private ProcessedFlowType processedFlowType;

    @Column(name = "description", nullable = false)
    private String description;

    public enum Direction {
        INBOUND, OUTBOUND
    }

    public enum ProcessedFlowType {
        MESSAGE, ALERTING, NOTIFICATION
    }


    public PartnerDTO toDto() {
        return PartnerDTO.builder()
                .id(id)
                .alias(alias)
                .type(type)
                .direction(direction)
                .application(application)
                .processedFlowType(processedFlowType)
                .description(description)
                .build();
    }

    public static Partner toEntity(PartnerDTO dto) {
        Partner p = new Partner();
        p.setAlias(dto.getDescription());
        p.setApplication(dto.getApplication());
        p.setType(dto.getType());
        p.setDirection(dto.getDirection());
        p.setDescription(dto.getDescription());
        p.setProcessedFlowType(dto.getProcessedFlowType());

        return p;

    }
}