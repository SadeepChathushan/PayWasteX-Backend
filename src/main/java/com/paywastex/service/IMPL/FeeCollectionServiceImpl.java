package com.paywastex.service.IMPL;

import com.paywastex.dto.FeeCollectionCardDto;
import com.paywastex.entity.Payment;
import com.paywastex.repository.PaymentRepository;
import com.paywastex.service.FeeCollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeCollectionServiceImpl implements FeeCollectionService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FeeCollectionCardDto> getCardsByZone(Long zoneId){
        List<Payment> payments = paymentRepository.findByCustomer_Zone_Id(zoneId);

        return payments.stream().map(payment -> {
            FeeCollectionCardDto dto = modelMapper.map(payment.getCustomer(), FeeCollectionCardDto.class);

            dto.setOutstandingDue(
                    payment.getAmountDue().subtract(payment.getPaidAmount() != null ? payment.getPaidAmount() : BigDecimal.ZERO)
            );

            dto.setStatus(payment.getStatus());
            dto.setZoneId(zoneId);

            // Add full name from ourusers (assuming Customer has a reference to OurUser)
            if (payment.getCustomer().getUser() != null) {
                dto.setFullName(payment.getCustomer().getUser().getFullName());
            }

            // Add business name from customer
            dto.setBusinessName(payment.getCustomer().getBusinessName());

            dto.setBusinessType(payment.getCustomer().getBusinessType());

            return dto;
        }).collect(Collectors.toList());
    }

}
