package com.expense.manager.e2e.hooks;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    public static WebDriver driver;

    @Before(order = 0)
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments(
            "--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,PasswordManagerRedesign"
        );

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @After(order = 0)
    public void teardown() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}