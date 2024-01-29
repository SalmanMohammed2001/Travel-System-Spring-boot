package com.travelserviceapi.travelserviceapi.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Booking {

    @Id
    private String bookingId;
    private String bookingDate;
    private double bookingPrice;
    private String bankSlip;
    private boolean bookingStatus;


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;


    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetails> bookingDetailsLis;





}
