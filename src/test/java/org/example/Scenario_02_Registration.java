package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestRegistration {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "E:\\_faks\\downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.ekupi.ba/";
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testRegisterWithInvalidData() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        Select  titula = new Select(webDriver.findElement(By.id("register.title")));
        titula.selectByIndex(2);

        webDriver.findElement(By.id("register.firstName")).sendKeys("Memo");
        webDriver.findElement(By.id("register.lastName")).sendKeys("Petarda");
        webDriver.findElement(By.id("register.email")).sendKeys("wrongemail");
        webDriver.findElement(By.id("password")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("register.checkPwd")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("registerChkTermsConditions")).click();

        webDriver.findElement(By.id("register-submit-btn")).click();
        //captcha
        Thread.sleep(10000);
        String message = webDriver.findElement(By.xpath("/html/body/main/div[5]/div[1]/div")).getText().split("\n")[1];
        assertEquals("Molimo ispravite greške", message);

        Thread.sleep(4000);
    }

    @Test
    void testRegisterWithMissingData() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        Select  titula = new Select(webDriver.findElement(By.id("register.title")));
        titula.selectByIndex(2);

        webDriver.findElement(By.id("register.firstName")).sendKeys("Memo");
        webDriver.findElement(By.id("register.lastName")).sendKeys("Petarda");
        webDriver.findElement(By.id("register.email")).sendKeys("wrongemail@gmail.com");
        webDriver.findElement(By.id("password")).sendKeys(""); // empty
        webDriver.findElement(By.id("register.checkPwd")).sendKeys(""); // empty
        webDriver.findElement(By.id("registerChkTermsConditions")).click();

        webDriver.findElement(By.id("register-submit-btn")).click();
        //captcha
        Thread.sleep(10000);
        String message = webDriver.findElement(By.xpath("/html/body/main/div[5]/div[1]/div")).getText().split("\n")[1];
        assertEquals("Molimo ispravite greške", message);

        Thread.sleep(4000);
    }

    String mail = "test1@gmai.com";

    @Test
    void testRegisterWithValidData() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        Select titula = new Select(webDriver.findElement(By.id("register.title")));
        titula.selectByIndex(2);

        String registerUrl = webDriver.getCurrentUrl();

        webDriver.findElement(By.id("register.firstName")).sendKeys("Memo");
        webDriver.findElement(By.id("register.lastName")).sendKeys("Petarda");
        webDriver.findElement(By.id("register.email")).sendKeys(mail);
        webDriver.findElement(By.id("password")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("register.checkPwd")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("registerChkTermsConditions")).click();

        webDriver.findElement(By.id("register-submit-btn")).click();
        //captcha
        Thread.sleep(10000);

        assertNotEquals(registerUrl, webDriver.getCurrentUrl());

        Thread.sleep(4000);
    }

    @Test
    void testRegisterWithExistingMail() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        Select  titula = new Select(webDriver.findElement(By.id("register.title")));
        titula.selectByIndex(2);

        String registerUrl = webDriver.getCurrentUrl();

        webDriver.findElement(By.id("register.firstName")).sendKeys("Memo");
        webDriver.findElement(By.id("register.lastName")).sendKeys("Petarda");
        webDriver.findElement(By.id("register.email")).sendKeys(mail);
        webDriver.findElement(By.id("password")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("register.checkPwd")).sendKeys("ajNakafubrate!");
        webDriver.findElement(By.id("registerChkTermsConditions")).click();

        webDriver.findElement(By.id("register-submit-btn")).click();
        //captcha
        Thread.sleep(10000);

        assertNotEquals(registerUrl, webDriver.getCurrentUrl());

        Thread.sleep(4000);
    }
}
