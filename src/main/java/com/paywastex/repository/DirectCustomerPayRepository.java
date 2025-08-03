package com.paywastex.repository;

import com.paywastex.entity.DirectCustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectCustomerPayRepository extends JpaRepository<DirectCustomerPayment, Long> {

}
