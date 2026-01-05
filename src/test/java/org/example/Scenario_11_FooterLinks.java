package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Scenario_11_FooterLinks {

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

    @BeforeEach
    public void hadleCookieNotification() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        try {
            webDriver.findElement(By.className("js-cookie-notification-settings")).click();
            webDriver.findElement(By.xpath("//*[@id=\"consent-management-alert-list\"]/li[2]/span[1]/label/input")).click();
            webDriver.findElement(By.xpath("//*[@id=\"consent-management-alert-list\"]/li[3]/span[1]/label/input")).click();
            webDriver.findElement(By.xpath("//*[@id=\"consent-management-alert\"]/button")).click();
        } catch (NoSuchElementException e) {
            System.out.println("No cookie notification, proceeding with the rest of the code immediately...");;
        }
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testTermsAndConditions() throws InterruptedException {
        webDriver.findElement(By.linkText("Opći uslovi poslovanja")).click();
        Thread.sleep(1000);
        String conditionsLink = webDriver.getCurrentUrl();
        assertTrue(conditionsLink.contains("Opci-uslovi-poslovanja"));
    }



    @Test
    void testPrivacyPolicy() throws InterruptedException {
        webDriver.findElement(By.linkText("Izjava o povjerljivosti")).click();
        Thread.sleep(1000);
        String conditionsLink = webDriver.getCurrentUrl();
        assertTrue(conditionsLink.contains("Izjava-o-povjerljivosti"));
    }

    @Test
    void testContact() throws InterruptedException {
        webDriver.findElement(By.linkText("Kontakt")).click();
        Thread.sleep(1000);
        String conditionsLink = webDriver.getCurrentUrl();
        assertTrue(conditionsLink.contains("Kontakt"));
    }

}
