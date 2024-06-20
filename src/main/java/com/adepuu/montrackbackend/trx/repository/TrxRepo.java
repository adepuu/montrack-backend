package com.adepuu.montrackbackend.trx.repository;

import com.adepuu.montrackbackend.trx.entity.Trx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepo extends JpaRepository<Trx, Long>{
}
