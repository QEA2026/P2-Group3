package com.expense.manager.e2e.hooks;

import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    public static WebDriver driver;

    @Before(order = 0)
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments(
            "--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,PasswordManagerRedesign"
        );

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setEnableDownloads(true);

        String seleniumUrl = System.getenv("SELENIUM_URL");

        if (seleniumUrl != null && !seleniumUrl.isBlank()) {
            driver = new RemoteWebDriver(new URL(seleniumUrl), options);
        } else {
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
    }

    @After(order = 0)
    public void teardown() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}