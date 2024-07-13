package kr.co.fastcampus.eatgo.util;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoggingTestWatcher implements TestWatcher {


    private static final Logger logger = LoggerFactory.getLogger(LoggingTestWatcher.class);

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("Test succeeded: {}", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("Test failed: {}", context.getDisplayName(), cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("Test aborted: {}", context.getDisplayName(), cause);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.info("Test disabled: {} with reason: {}", context.getDisplayName(), reason.orElse("No reason"));
    }
}
