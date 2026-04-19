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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayService {

    private final DataSource dataSource;
    private final Random random = new Random();

    public String processPay(String userId) {

        log.info("Payment request userId={}", userId);

        try {
            switch (userId) {

                case "slow":
                    sleep(2000);
                    break;

                case "db":
                    slowDb(5); // 강하게
                    break;

                case "db_slow":
                    sleep(1000);
                    slowDb(5);
                    break;

                case "fail":
                    throw new PayException(PayErrorCode.PAYMENT_FAILED);

                case "random":
                    if (random.nextInt(3) == 0) {
                        throw new PayException(PayErrorCode.PAYMENT_FAILED);
                    }
                    break;

                case "crash":
                    System.exit(1);
                    break;
            }

            return "Payment success";

        } catch (Exception e) {
            log.error("Payment failed userId={}", userId, e);
            throw e;
        }
    }

    private void slowDb(int seconds) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT pg_sleep(?)")) {

            ps.setInt(1, seconds);
            ps.execute();

        } catch (SQLException e) {
            throw new PayException(PayErrorCode.DB_ERROR, e);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PayException(PayErrorCode.INTERRUPTED, e);
        }
    }
}
