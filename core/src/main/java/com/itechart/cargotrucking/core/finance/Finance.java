package com.itechart.cargotrucking.core.finance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "finance")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "sys_admin_payment", nullable = false)
    private double sysAdminPayment;

    @Column(name = "db_admin_payment", nullable = false)
    private double dbAdminPayment;

    @Column(name = "server_payment", nullable = false)
    private double serverPayment;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
}
