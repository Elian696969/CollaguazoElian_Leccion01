package com.espe.support.dto;

import com.espe.support.enums.Currency;
import com.espe.support.enums.TicketPriority;
import com.espe.support.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SupportTicketCreateDTO {
    
    @NotBlank(message = "El nombre del solicitante es obligatorio")
    @Size(max = 100, message = "El nombre del solicitante no puede exceder 100 caracteres")
    private String requesterName;
    
    @NotNull(message = "El estado es obligatorio")
    private TicketStatus status;
    
    @NotNull(message = "La prioridad es obligatoria")
    private TicketPriority priority;
    
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String category;
    
    private BigDecimal estimatedCost;
    
    private Currency currency;
    
    private LocalDate dueDate;
    
    // Getters and Setters
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
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
