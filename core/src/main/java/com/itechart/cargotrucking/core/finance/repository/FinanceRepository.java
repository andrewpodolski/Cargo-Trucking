package com.itechart.cargotrucking.core.finance.repository;

import com.itechart.cargotrucking.core.common.jpainterface.QuerydslRepository;
import com.itechart.cargotrucking.core.finance.Finance;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends QuerydslRepository<Finance, Long> {
    Finance findFirstByOrderByIdDesc();
}
