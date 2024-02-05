package com.travelserviceapi.travelserviceapi.service;

import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseYearlyIncome;

import java.util.List;

public interface YearlyIncomeService {

    public List<ResponseYearlyIncome> findYearlyIncome();
}
