package com.hj.pay_service.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.hj.pay_service.exception.PayErrorCode;
import com.hj.pay_service.exception.PayException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayService {

    private final DataSource dataSource;

    public String processPay(String userId) {
        log.info("pay service: "+userId);

        switch (userId) {

            case "slow":
                sleep(2000);
                break;

            case "db":
                slowDb();
                break;

            case "fail":
                throw new PayException(PayErrorCode.PAYMENT_FAILED);

            case "db_slow":
                sleep(1000);
                slowDb();
                break;

            default:
                // normal
        }

        return "Payment success";
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PayException(PayErrorCode.INTERRUPTED, e);
        }
    }

    private void slowDb() {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT pg_sleep(2)")) {
            ps.execute();
        } catch (SQLException e) {
            throw new PayException(PayErrorCode.DB_ERROR, e);
        }
    }
}