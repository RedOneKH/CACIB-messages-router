package com.redone.messages_router.service;

import com.redone.messages_router.dto.PartnerDTO;
import com.redone.messages_router.model.Partner;
import com.redone.messages_router.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Page<PartnerDTO> getAllPartners(Pageable pageable) {
        Page<Partner> partnerPage = partnerRepository.findAll(pageable);
        return partnerPage.map(Partner::toDto);
    }

    @Override
    public PartnerDTO getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElse(null);
        return partner.toDto();
    }


    @Override
    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        validatePartner(partnerDTO);
        Partner partner = Partner.toEntity(partnerDTO);
        Partner savedPartner = partnerRepository.save(partner);
        return savedPartner.toDto();
    }

    @Override
    public void deletePartner(Long id) throws Exception {
        if (!partnerRepository.existsById(id)) {
            throw new Exception("Partner not found with id: " + id);
        }
        partnerRepository.deleteById(id);
    }

    private void validatePartner(PartnerDTO partner) {
        if (partner.getAlias() == null || partner.getType() == null || partner.getDescription() == null) {
            throw new IllegalArgumentException("Alias, Type and Description are required");
        }
    }
}
