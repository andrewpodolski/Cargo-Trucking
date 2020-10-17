package com.itechart.cargotrucking.core.finance.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FinanceInfoDto {
    private long id;
    private BigDecimal sysAdminPayment;
    private BigDecimal dbAdminPayment;
    private BigDecimal serverPayment;
    private LocalDateTime creationDate;
}
