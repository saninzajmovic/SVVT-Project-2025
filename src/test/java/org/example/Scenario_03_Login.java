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

public class Scenario_03_Login {

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
    void testLoginWithInvalidMail() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        String loginUrl = webDriver.getCurrentUrl();

        webDriver.findElement(By.id("j_username")).sendKeys("-1");
        webDriver.findElement(By.id("j_password")).sendKeys("ajNakafubrate!");

        webDriver.findElement(By.id("submit")).click();

        Thread.sleep(1000);

        assertEquals(loginUrl, webDriver.getCurrentUrl());

        Thread.sleep(4000);
    }

    String mail = "test1@gmai.com";

    @Test
    void testLoginWithMissingData() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        webDriver.findElement(By.id("j_username")).sendKeys(mail);
        // no input for password

        webDriver.findElement(By.id("submit")).click();

        Thread.sleep(1000);
        String message = webDriver.findElement(By.xpath("/html/body/main/div[5]/div[1]/div")).getText().split("\n")[1];
        assertEquals("Email adresa ili lozinka su neispravni.", message);

        Thread.sleep(4000);
    }

    @Test
    void testLoginWithValidData() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        String registerUrl = webDriver.getCurrentUrl();

        webDriver.findElement(By.id("j_username")).sendKeys(mail);
        webDriver.findElement(By.id("j_password")).sendKeys("ajNakafubrate!");

        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(1000);

        String welcomeMessage = webDriver.findElement(By.xpath("/html/body/main/header/nav[2]/div/div[2]/div[2]/ul[2]/li[2]")).getText();

        assertNotEquals(registerUrl, webDriver.getCurrentUrl());
        assertEquals("Dobrodošli Memo", welcomeMessage);

        Thread.sleep(4000);
    }
}
