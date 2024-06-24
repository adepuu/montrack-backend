package com.adepuu.montrackbackend.trx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // fields and methods
    private Long id;
    private String transactionType;
    private BigDecimal value;
    private String currency;
    private String description;
    private String attachment;
    private Instant transactionDate;
}
