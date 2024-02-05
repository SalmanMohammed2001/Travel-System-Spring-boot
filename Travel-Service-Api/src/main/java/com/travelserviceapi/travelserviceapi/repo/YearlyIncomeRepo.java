package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.entity.YearlyIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YearlyIncomeRepo extends JpaRepository<YearlyIncomeEntity,String> {
    @Query(value = "SELECT  booking_date AS year, SUM(booking_price) AS yearly_income FROM booking GROUP BY booking_date ORDER BY booking_date",nativeQuery = true)
    public List<YearlyIncomeEntity> findByYearlyIncome();
}
