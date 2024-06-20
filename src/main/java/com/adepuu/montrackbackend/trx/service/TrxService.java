package com.adepuu.montrackbackend.trx.service;

import com.adepuu.montrackbackend.trx.dto.TransactionDetailResponse;

public interface TrxService {
    TransactionDetailResponse getTransactionDetail(Long id);
}
