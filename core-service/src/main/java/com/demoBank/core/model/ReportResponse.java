package com.demoBank.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ReportResponse(
        String clientName,
        BigDecimal totalAccountsBalance,
        LocalDateTime generatedAt,
        Map<String, List<TransactionReport>> accounts
        ) { }
