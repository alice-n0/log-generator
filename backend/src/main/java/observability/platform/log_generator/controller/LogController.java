package observability.platform.log_generator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log-test")    
    public String getLogs() {

        log.info("INFO log from /log-test endpoint");
        log.warn("WARN log from /log-test endpoint");
        log.error("ERROR log from /log-test endpoint");

        return "ok";
    }
}
