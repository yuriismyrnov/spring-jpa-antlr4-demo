package com.levi9.demo.wallet.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "wallets")
public class Wallet implements Persistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    private Currency currency;

    @ManyToOne(optional = false)
    private User user;

    @Version
    private int version = 0;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
