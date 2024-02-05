package com.travelserviceapi.travelserviceapi.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class YearlyIncomeEntity {

    @Id
    private String year;

    @Column(name = "yearly_income")
    private Double yearlyIncome;

}