package com.levi9.demo.wallet.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "transactions")
public class Transaction implements Persistable<UUID> {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Wallet from;

    @ManyToOne(optional = false)
    private Wallet to;

    private BigDecimal amount;

    private Instant datetime;

    @Version
    private Integer version;

    public static Transaction create(Wallet from, Wallet to, BigDecimal amount) {
        var transaction = new Transaction();
        transaction.from = from;
        transaction.to = to;
        transaction.amount = amount;
        transaction.datetime = Instant.now();
        return transaction;
    }

    public BigDecimal getAmount() {
        return amount.setScale(to.getCurrency().getFractionDigits(), RoundingMode.HALF_UP);
    }

    @Override
    public boolean isNew() {
        return version == null;
    }
}
