package com.itechart.cargotrucking.webapp.user.admin;

import com.itechart.cargotrucking.core.finance.Finance;
import com.itechart.cargotrucking.core.finance.dto.FinanceUpdateDto;
import com.itechart.cargotrucking.core.finance.service.FinanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/finance")
public class AdminController {
    private FinanceService financeService;

    public AdminController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping
    public ResponseEntity<String> updateFinance(@Valid @RequestBody FinanceUpdateDto finance) {
        long id = financeService.updateFinance(finance);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/latestFinance")
    public Finance getLatestFinance() {
        return financeService.getLatestFinance();
    }
}
