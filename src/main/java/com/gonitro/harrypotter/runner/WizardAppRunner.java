package com.gonitro.harrypotter.runner;

import com.gonitro.harrypotter.service.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WizardAppRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WizardAppRunner.class);

    private final ConsoleService consoleService;

    public WizardAppRunner(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            consoleService.runInteractiveMode();
        } catch (Exception ex) {
            LOGGER.error("Error running application", ex);
            System.exit(1);
        }
    }
}
