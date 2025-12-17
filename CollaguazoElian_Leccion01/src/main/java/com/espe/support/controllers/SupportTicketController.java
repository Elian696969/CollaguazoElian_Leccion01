package com.espe.support.controllers;

import com.espe.support.dto.SupportTicketDTO;
import com.espe.support.dto.SupportTicketCreateDTO;
import com.espe.support.services.SupportTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/support-tickets")
@Tag(name = "Support Ticket API", description = "API para la gestión de tickets de soporte técnico")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @Autowired
    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @PostMapping
    @Operation(summary = "Crear ticket", description = "Registra un nuevo ticket de soporte técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de ticket inválidos")
    })
    public ResponseEntity<SupportTicketDTO> createTicket(
            @Parameter(description = "Datos del nuevo ticket") @Valid @RequestBody SupportTicketCreateDTO createDTO) {
        SupportTicketDTO savedTicket = supportTicketService.createTicket(createDTO);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar tickets con filtros", description = "Retorna una lista paginada de tickets con filtros opcionales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros de filtro inválidos")
    })
    public ResponseEntity<Page<SupportTicketDTO>> getTicketsWithFilters(
            @Parameter(description = "Búsqueda de texto (ticketNumber, requesterName)") @RequestParam(required = false) String q,
            @Parameter(description = "Estado del ticket (OPEN, IN_PROGRESS, RESOLVED, CLOSED, CANCELLED)") @RequestParam(required = false) String status,
            @Parameter(description = "Moneda (USD, EUR)") @RequestParam(required = false) String currency,
            @Parameter(description = "Costo estimado mínimo") @RequestParam(required = false) BigDecimal minCost,
            @Parameter(description = "Costo estimado máximo") @RequestParam(required = false) BigDecimal maxCost,
            @Parameter(description = "Fecha desde (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) String from,
            @Parameter(description = "Fecha hasta (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam(required = false) String to,
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo de ordenamiento") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Dirección de ordenamiento") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        
        if (from != null && !from.trim().isEmpty()) {
            try {
                fromDate = LocalDateTime.parse(from);
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de fecha 'from' inválido. Use yyyy-MM-dd'T'HH:mm:ss");
            }
        }
        
        if (to != null && !to.trim().isEmpty()) {
            try {
                toDate = LocalDateTime.parse(to);
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de fecha 'to' inválido. Use yyyy-MM-dd'T'HH:mm:ss");
            }
        }
        
        Page<SupportTicketDTO> ticketPage = supportTicketService.getTicketsWithFilters(
                q, status, currency, minCost, maxCost, fromDate, toDate, pageable);
        
        return new ResponseEntity<>(ticketPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ticket por ID", description = "Retorna un ticket específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<SupportTicketDTO> getTicketById(
            @Parameter(description = "ID del ticket") @PathVariable Long id) {
        SupportTicketDTO ticket = supportTicketService.getTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ticket", description = "Elimina un ticket del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ticket eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    public ResponseEntity<Void> deleteTicket(
            @Parameter(description = "ID del ticket a eliminar") @PathVariable Long id) {
        if (!supportTicketService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        supportTicketService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
