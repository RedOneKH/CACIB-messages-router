package com.redone.messages_router.service;

import com.redone.messages_router.dto.PartnerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    Page<PartnerDTO> getAllPartners(Pageable pageable);
    PartnerDTO getPartnerById(Long id);
    PartnerDTO createPartner(PartnerDTO partnerDTO);
    void deletePartner(Long id) throws Exception;
}
