package com.itechart.cargotrucking.core.report.sysadmin.service;

import com.itechart.cargotrucking.core.client.ClientStatusEnum;
import com.itechart.cargotrucking.core.client.ClientStatusHistory;
import com.itechart.cargotrucking.core.client.repository.ClientRepository;
import com.itechart.cargotrucking.core.client.repository.ClientStatusRepository;
import com.itechart.cargotrucking.core.finance.repository.FinanceRepository;
import com.itechart.cargotrucking.core.report.exception.InvalidDateIntervalException;
import com.itechart.cargotrucking.core.report.sysadmin.SysAdminReport;
import com.itechart.cargotrucking.core.report.sysadmin.dto.IntervalStatistics;
import com.itechart.cargotrucking.core.report.sysadmin.dto.SysAdminReportCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysAdminReportServiceImpl implements SysAdminReportService {
    private ClientStatusRepository clientStatusRepository;
    private FinanceRepository financeRepository;
    private ClientRepository clientRepository;

    @Autowired
    public SysAdminReportServiceImpl(ClientStatusRepository clientStatusRepository, FinanceRepository financeRepository, ClientRepository clientRepository) {
        this.clientStatusRepository = clientStatusRepository;
        this.financeRepository = financeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public SysAdminReport createSysAdminReport(SysAdminReportCreateDto createDto) {
        validateReport(createDto);

        long activeClients = 0;
        long lostClients = 0;
        double income = clientRepository.getIncome();
        double consumption;

        double sysAdminSalary = financeRepository.findFirstByOrderByIdDesc().getSysAdminPayment();
        double dbAdminSalary = financeRepository.findFirstByOrderByIdDesc().getDbAdminPayment();
        double serverPayment = financeRepository.findFirstByOrderByIdDesc().getServerPayment();

        LocalDateTime start = createDto.getInitialDate().atStartOfDay();
        LocalDateTime end = createDto.getFinalDate().atStartOfDay().plusDays(1);
        int interval = (int) ChronoUnit.DAYS.between(start, end) - 1;

        List<ClientStatusHistory> histories = clientStatusRepository.findAll(
                start, end,
                Sort.by(Sort.Direction.DESC, "date")
        );
        Set<Long> set = new HashSet<>();

        for (ClientStatusHistory clientStatusHistory : histories) {
            if (!set.contains(clientStatusHistory.getClientId())) {
                set.add(clientStatusHistory.getClientId());
                if (clientStatusHistory.getStatus().equals(ClientStatusEnum.ACTIVATED)) {
                    activeClients++;
                } else {
                    lostClients++;
                }
            }
        }

        consumption = (dbAdminSalary + sysAdminSalary + serverPayment) * interval / 30;

        IntervalStatistics statistics = new IntervalStatistics(createDto.getInitialDate(), createDto.getFinalDate(), consumption, income, activeClients, lostClients);

        return new SysAdminReport(statistics);
    }

    private void validateReport(SysAdminReportCreateDto createDto) {
        if (createDto.getFinalDate().isBefore(createDto.getInitialDate())) {
            throw new InvalidDateIntervalException();
        }
    }
}
