package com.hj.pay_service.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.hj.pay_service.exception.PayErrorCode;
import com.hj.pay_service.exception.PayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayService {

    private final Random random = new Random();
    private final DataSource dataSource;

    public String processPay(String userId) {

        // 1. 외부 API latency 시뮬레이션
        if (random.nextInt(100) < 20) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 중요
                throw new PayException(PayErrorCode.INTERRUPTED, e);
            }
        }

        // 2. DB slow query
        if (random.nextInt(100) < 10) {
            try (Connection conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT pg_sleep(2)")) {
                ps.execute();
            } catch (SQLException e) {
                throw new PayException(PayErrorCode.DB_ERROR, e);
            }
        }

        // 3. 일부 실패
        if (random.nextInt(100) < 5) {
            throw new PayException(PayErrorCode.PAYMENT_FAILED);
        }

        return "Payment success";
    }
}
