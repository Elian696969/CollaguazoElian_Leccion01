package com.espe.support.dto;

import com.espe.support.enums.Currency;
import com.espe.support.enums.TicketPriority;
import com.espe.support.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SupportTicketDTO {
    
    private Long id;
    private String ticketNumber;
    private String requesterName;
    private TicketStatus status;
    private TicketPriority priority;
    private String category;
    private BigDecimal estimatedCost;
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTicketNumber() {
        return ticketNumber;
    }
    
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    
    public String getRequesterName() {
        return requesterName;
    }
    
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public TicketStatus getStatus() {
        return status;
    }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    
    public TicketPriority getPriority() {
        return priority;
    }
    
    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }
    
    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
