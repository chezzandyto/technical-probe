package com.demoBank.core.repository;

import com.demoBank.core.model.entity.ReportView;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ReportViewRepository extends R2dbcRepository<ReportView, Long> {
    @Query("SELECT * from MOVREPORT m WHERE m.id = :accountId AND m.dateTime >= :from AND m.dateTime < :to ORDER BY m.dateTime DESC")
    Flux<ReportView> getReport(Long accountId, LocalDateTime from, LocalDateTime to);
}
