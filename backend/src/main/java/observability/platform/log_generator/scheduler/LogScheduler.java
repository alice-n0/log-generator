package observability.platform.log_generator.scheduler;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogScheduler {
    private static final Logger log = LoggerFactory.getLogger(LogScheduler.class);
    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void generateLogs() {
        log.info("INFO log from LogScheduler");

        int rand = random.nextInt(10);

        if (rand > 7) {
            log.error("ERROR log from LogScheduler");
        } else if (rand > 4) {
            log.warn("ERROR log from LogScheduler");
        }
    }
}
