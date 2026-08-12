package com.expense.manager.e2e.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement waitFor(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void enterUsername(String username) {
        WebElement field = waitFor(By.id("username"));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = waitFor(By.id("password"));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        waitFor(By.cssSelector("button[type='submit']")).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}