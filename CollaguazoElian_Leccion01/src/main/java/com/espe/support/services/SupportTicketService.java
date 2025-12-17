package com.espe.support.services;

import com.espe.support.dto.SupportTicketDTO;
import com.espe.support.dto.SupportTicketCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SupportTicketService {
    
    SupportTicketDTO createTicket(SupportTicketCreateDTO createDTO);
    
    Page<SupportTicketDTO> getTicketsWithFilters(
            String q,
            String status,
            String currency,
            BigDecimal minCost,
            BigDecimal maxCost,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);
    
    SupportTicketDTO getTicketById(Long id);
    
    boolean existsById(Long id);
    
    void deleteById(Long id);
}
