package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseBookingDetailsDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseYearlyIncome;
import com.travelserviceapi.travelserviceapi.entity.YearlyIncomeEntity;
import com.travelserviceapi.travelserviceapi.repo.YearlyIncomeRepo;
import com.travelserviceapi.travelserviceapi.service.YearlyIncomeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearlyIncomeServiceImpl implements YearlyIncomeService {

    private final YearlyIncomeRepo yearlyIncomeRepo;

    private final ModelMapper mapper;

    @Autowired
    public YearlyIncomeServiceImpl(YearlyIncomeRepo yearlyIncomeRepo, ModelMapper mapper) {
        this.yearlyIncomeRepo = yearlyIncomeRepo;
        this.mapper = mapper;
    }

    @Override
    public List<ResponseYearlyIncome> findYearlyIncome() {
        List<YearlyIncomeEntity> byYearlyIncome = yearlyIncomeRepo.findByYearlyIncome();
       return mapper.map(byYearlyIncome,new TypeToken<List<ResponseYearlyIncome>>(){}.getType());

      /*  byYearlyIncome.forEach(data->{
            System.out.println(data.getYearlyIncome());
            System.out.println(data.getYear());
        });
        return  null;*/
    }
}
