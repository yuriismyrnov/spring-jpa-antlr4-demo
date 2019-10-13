package com.levi9.demo.wallet.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "currencies")
public class Currency implements Persistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "numeric_code")
    private int numericCode;

    @Column(name = "fraction_digits")
    private int fractionDigits;

    @Version
    private int version = 0;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
