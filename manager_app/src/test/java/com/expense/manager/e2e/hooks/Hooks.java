package com.expense.manager.e2e.hooks;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    public static WebDriver driver;

    @Before(order = 0)
    public void setup() throws URISyntaxException, MalformedURLException {
        String seleniumUrl = System.getenv("SELENIUM_URL");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (seleniumUrl != null && !seleniumUrl.isBlank()) {
            driver = new RemoteWebDriver(new URI(seleniumUrl).toURL(), options);
        } else {
            // local fallback, e.g. running tests outside Docker
            options.addArguments("--headless=new");
            driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        }

        driver.manage().window().maximize();
    }

    @After(order = 0)
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}