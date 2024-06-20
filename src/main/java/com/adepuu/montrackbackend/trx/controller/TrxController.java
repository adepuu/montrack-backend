package com.adepuu.montrackbackend.trx.controller;

import com.adepuu.montrackbackend.auth.helpers.Claims;
import com.adepuu.montrackbackend.responses.Response;
import com.adepuu.montrackbackend.trx.service.TrxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trx")
public class TrxController {
    private final TrxService trxService;

    public TrxController(TrxService trxService) {
        this.trxService = trxService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getTransactionDetail(@PathVariable Long id) {
        return Response.success("Transaction detail fetched", trxService.getTransactionDetail(id));
    }
}
