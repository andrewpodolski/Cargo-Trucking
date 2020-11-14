package com.itechart.cargotrucking.core.finance.service;

import com.itechart.cargotrucking.core.car.Car;
import com.itechart.cargotrucking.core.car.dto.CarAddDto;
import com.itechart.cargotrucking.core.finance.Finance;
import com.itechart.cargotrucking.core.finance.dto.FinanceInfoDto;
import com.itechart.cargotrucking.core.finance.dto.FinanceUpdateDto;
import com.itechart.cargotrucking.core.finance.repository.FinanceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
public class FinanceServiceImpl implements FinanceService {
    private FinanceRepository financeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public FinanceServiceImpl(FinanceRepository financeRepository, ModelMapper modelMapper) {
        this.financeRepository = financeRepository;
        this.modelMapper = modelMapper;

        this.modelMapper.addMappings(new PropertyMap<FinanceInfoDto, Finance>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }

    @Override
    public long updateFinance(FinanceUpdateDto financeDto) {
        financeDto.setCreationDate(LocalDateTime.now());
        Finance finance = modelMapper.map(financeDto, Finance.class);
        financeRepository.save(finance);

        return finance.getId();
    }

    @Override
    public Finance getLatestFinance() {
        return financeRepository.findFirstByOrderByIdDesc();
    }
}
