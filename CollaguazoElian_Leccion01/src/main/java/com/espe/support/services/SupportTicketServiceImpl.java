package com.espe.support.services;

import com.espe.support.dto.SupportTicketDTO;
import com.espe.support.dto.SupportTicketCreateDTO;
import com.espe.support.enums.Currency;
import com.espe.support.enums.TicketStatus;
import com.espe.support.exceptions.ResourceNotFoundException;
import com.espe.support.mappers.SupportTicketMapper;
import com.espe.support.models_entities.SupportTicket;
import com.espe.support.repositories.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class SupportTicketServiceImpl implements SupportTicketService {
    
    private final SupportTicketRepository supportTicketRepository;
    private final SupportTicketMapper supportTicketMapper;
    
    @Autowired
    public SupportTicketServiceImpl(SupportTicketRepository supportTicketRepository, 
                                   SupportTicketMapper supportTicketMapper) {
        this.supportTicketRepository = supportTicketRepository;
        this.supportTicketMapper = supportTicketMapper;
    }
    
    @Override
    public SupportTicketDTO createTicket(SupportTicketCreateDTO createDTO) {
        SupportTicket ticket = supportTicketMapper.toEntity(createDTO);
        SupportTicket savedTicket = supportTicketRepository.save(ticket);
        return supportTicketMapper.toDTO(savedTicket);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDTO> getTicketsWithFilters(
            String q,
            String status,
            String currency,
            BigDecimal minCost,
            BigDecimal maxCost,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable) {
        
        TicketStatus statusEnum = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                statusEnum = TicketStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + status);
            }
        }
        
        Currency currencyEnum = null;
        if (currency != null && !currency.trim().isEmpty()) {
            try {
                currencyEnum = Currency.valueOf(currency.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Moneda inválida: " + currency);
            }
        }
        
        if (minCost != null && minCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El costo mínimo debe ser mayor o igual a cero");
        }
        
        if (maxCost != null && maxCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El costo máximo debe ser mayor o igual a cero");
        }
        
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("La fecha 'from' debe ser anterior o igual a la fecha 'to'");
        }
        
        Page<SupportTicket> tickets = supportTicketRepository.findWithFilters(
                q != null && !q.trim().isEmpty() ? q : null,
                statusEnum,
                currencyEnum,
                minCost,
                maxCost,
                from,
                to,
                pageable);
        
        return tickets.map(supportTicketMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SupportTicketDTO getTicketById(Long id) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + id));
        return supportTicketMapper.toDTO(ticket);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return supportTicketRepository.existsById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        if (!supportTicketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket no encontrado con ID: " + id);
        }
        supportTicketRepository.deleteById(id);
    }
}
