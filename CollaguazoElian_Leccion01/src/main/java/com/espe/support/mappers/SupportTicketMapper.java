package com.espe.support.mappers;

import com.espe.support.dto.SupportTicketCreateDTO;
import com.espe.support.dto.SupportTicketDTO;
import com.espe.support.models_entities.SupportTicket;
import org.springframework.stereotype.Component;

@Component
public class SupportTicketMapper {
    
    public SupportTicketDTO toDTO(SupportTicket entity) {
        if (entity == null) {
            return null;
        }
        
        SupportTicketDTO dto = new SupportTicketDTO();
        dto.setId(entity.getId());
        dto.setTicketNumber(entity.getTicketNumber());
        dto.setRequesterName(entity.getRequesterName());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setCategory(entity.getCategory());
        dto.setEstimatedCost(entity.getEstimatedCost());
        dto.setCurrency(entity.getCurrency());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDueDate(entity.getDueDate());
        
        return dto;
    }
    
    public SupportTicket toEntity(SupportTicketCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        
        SupportTicket entity = new SupportTicket();
        entity.setRequesterName(createDTO.getRequesterName());
        entity.setStatus(createDTO.getStatus());
        entity.setPriority(createDTO.getPriority());
        entity.setCategory(createDTO.getCategory());
        entity.setEstimatedCost(createDTO.getEstimatedCost());
        entity.setCurrency(createDTO.getCurrency());
        entity.setDueDate(createDTO.getDueDate());
        
        return entity;
    }
}
