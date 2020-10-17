package com.itechart.cargotrucking.core.finance.service;

import com.itechart.cargotrucking.core.finance.Finance;
import com.itechart.cargotrucking.core.finance.dto.FinanceUpdateDto;

import javax.transaction.Transactional;

public interface FinanceService {
    @Transactional
    long updateFinance(FinanceUpdateDto financeDto);

    @Transactional
    Finance getLatestFinance();
}
