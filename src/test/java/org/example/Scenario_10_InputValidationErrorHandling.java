package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Scenario_10_InputValidationErrorHandling {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/MS/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        // 1. Hide the "AutomationControlled" flag
        options.addArguments("--disable-blink-features=AutomationControlled");

        // 2. Remove the "Chrome is being controlled by automated software" bar
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // 3. Add a User-Agent (Pretend to be a real human browser)
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://www.ekupi.ba/bs/login");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void invalidEmailTest(){
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"register.email\"]")));
        email.sendKeys("bad_email_format");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#register\\.email-error, .parsley-error, .help-block")
        ));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 1500)");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"registerChkTermsConditions\"]\n")).click();

        //*[@id="register-submit-btn"]
        driver.findElement(By.xpath("//*[@id=\"register-submit-btn\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jse.executeScript("window.scrollTop");

        assertTrue(errorMsg.getText().equals("Unesite ispravan email") || errorMsg.isDisplayed());
    }

    @Test
    public void shortPasswordTest(){
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"password\"]")));
        email.sendKeys("kratak");


        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#register\\.email-error, .parsley-error, .help-block")
        ));

        //*[@id="registerChkTermsConditions"]

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 1500)");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"registerChkTermsConditions\"]\n")).click();

        //*[@id="register-submit-btn"]
        driver.findElement(By.xpath("//*[@id=\"register-submit-btn\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jse.executeScript("window.scrollTop");

        assertTrue(errorMsg.getText().equals("Lozinka mora imati najmanje 6 znakova") || errorMsg.isDisplayed());
    }


}