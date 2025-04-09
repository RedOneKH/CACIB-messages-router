package com.redone.messages_router.controller;

import com.redone.messages_router.dto.PartnerDTO;
import com.redone.messages_router.model.Partner;
import com.redone.messages_router.service.PartnerService;
import com.redone.messages_router.service.PartnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@CrossOrigin(origins = "*")
public class PartnerController {
    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<Page<PartnerDTO>> getAllPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(partnerService.getAllPartners(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerDTO partnerDTO) {
        PartnerDTO createdPartner = partnerService.createPartner(partnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPartner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        try {
            partnerService.deletePartner(id);
        } catch (Exception e) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}