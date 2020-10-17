package com.itechart.cargotrucking.core.finance.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FinanceUpdateDto {
    @NotNull(message = "System Admin Payment cannot be empty")
    @Min(value = 1, message = "System Admin Payment should be more than 1")
    private BigDecimal sysAdminPayment;

    @NotNull(message = "DB Admin Payment cannot be empty")
    @Min(value = 1, message = "DB Admin Payment should be more than 1")
    private BigDecimal dbAdminPayment;

    @NotNull(message = "Server Payment cannot be blank")
    @Min(value = 1, message = "Server Payment cannot be null")
    private BigDecimal serverPayment;

    private LocalDateTime creationDate;
}
