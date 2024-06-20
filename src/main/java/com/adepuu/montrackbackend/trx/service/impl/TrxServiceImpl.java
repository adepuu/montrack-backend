package com.adepuu.montrackbackend.trx.service.impl;

import com.adepuu.grpcinterface.lib.CurrencyExchangeGrpc.CurrencyExchangeBlockingStub;
import com.adepuu.grpcinterface.lib.ExchangeRequest;
import com.adepuu.grpcinterface.lib.ExchangeResponse;
import com.adepuu.montrackbackend.exceptions.DataNotFoundException;
import com.adepuu.montrackbackend.trx.dto.TransactionDetailResponse;
import com.adepuu.montrackbackend.trx.repository.TrxRepo;
import com.adepuu.montrackbackend.trx.service.TrxService;
import com.adepuu.montrackbackend.users.service.UserService;
import io.grpc.StatusRuntimeException;
import lombok.extern.java.Log;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@Log
@Service
public class TrxServiceImpl implements TrxService  {
    private final TrxRepo trxRepo;
    private final UserService userService;

    // GRPC client for currency exchange service
    @GrpcClient("exchange-server")
    private CurrencyExchangeBlockingStub currencyExchangeStub;

    private final String[] trxType = {"Income", "Expense"};

    public TrxServiceImpl(TrxRepo trxRepo, UserService userService) {
        this.trxRepo = trxRepo;
        this.userService = userService;
    }

    @Override
    public TransactionDetailResponse getTransactionDetail(Long id) {
        var user = userService.profile();
        log.info("User's display currency : " + user.getCurrency().getName());
        return trxRepo.findById(id).map(trx -> {
            var response = new TransactionDetailResponse();
            response.setId(trx.getId());
            response.setTransactionType(trxType[trx.getTrxTypeId()]);
            response.setValue(trx.getValue());
            response.setDescription(trx.getDescription());
            response.setAttachment(trx.getAttachment());
            response.setTransactionDate(trx.getCreatedAt());

            if (!Objects.equals(trx.getCurrency().getId(), user.getCurrency().getId())) {
                String currencyPair = trx.getCurrency().getName() + "/" + user.getCurrency().getName();
                log.info("Converting transaction value to user's display currency. Currency pair: " + currencyPair);
                try {
                    ZoneId zone = ZoneId.of("Asia/Jakarta");
                    LocalDate transactionDate = LocalDate.ofInstant(trx.getCreatedAt(), zone);
                    ExchangeRequest request = ExchangeRequest.newBuilder()
                            .setAmount(trx.getValue().toString())
                            .setPair(currencyPair)
                            .setDate(transactionDate.toString())
                            .build();
                    final ExchangeResponse resp = this.currencyExchangeStub.getExchangeAmount(request);
                    log.info("Converted to " + user.getCurrency().getName() + " " + resp.getExchangedAmount());
                    BigDecimal exchangedAmount = BigDecimal.valueOf(resp.getExchangedAmount());


                    response.setValue(exchangedAmount);
                    response.setCurrency(user.getCurrency().getName());
                } catch (final StatusRuntimeException e) {
                    log.info("FAILED with " + e.getStatus().getCode().name());
                }
            }
            return response;
        }).orElseThrow(() -> new DataNotFoundException("Transaction not found"));
    }
}
