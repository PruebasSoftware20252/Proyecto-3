package com.inventario.util;

import java.time.Duration;
import java.util.function.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WaitUtil {
    public static WebElement waitVisible(WebDriver driver, By locator, int sec) {
        return new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitRefreshedVisible(WebDriver driver, By locator, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        return wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    public static <T> T retryStale(WebDriver driver, int attempts, Function<WebDriver, T> action) {
        RuntimeException last = null;
        for (int i = 0; i < attempts; i++) {
            try { return action.apply(driver); }
            catch (StaleElementReferenceException e) { last = e; }
        }
        throw last != null ? last : new RuntimeException("retryStale failed");
    }
}
