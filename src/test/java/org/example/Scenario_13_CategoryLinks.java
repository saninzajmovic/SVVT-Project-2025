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
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class Scenario_13_CategoryLinks {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "E:\\_faks\\downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.ekupi.ba/";
    }

    @BeforeEach
    public void navigateToHome() throws InterruptedException{
        webDriver.get(baseUrl);
        Thread.sleep(1000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testFridges() throws InterruptedException {
        WebElement kucanskiAparati = webDriver.findElement(By.linkText("Kućanski aparati"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(kucanskiAparati).perform();
        Thread.sleep(100);
        webDriver.findElement(By.linkText("Frižideri")).click();
        Thread.sleep(4000);
        try {
            WebElement result = webDriver.findElement(By.partialLinkText("frižider"));
            assertTrue(true);
        } catch (NoSuchElementException e) {
            fail("There should be 'frižider' in results");
        }
    }

    @Test
    void testMobiteli() throws InterruptedException {
        WebElement elektronika = webDriver.findElement(By.linkText("Elektronika"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(elektronika).perform();
        Thread.sleep(100);
        webDriver.findElement(By.linkText("Mobiteli")).click();
        Thread.sleep(4000);
        try {
            WebElement result = webDriver.findElement(By.partialLinkText("mobitel"));
            assertTrue(true);
        } catch (NoSuchElementException e) {
            fail("There should be 'mobitel' in results");
        }
    }

    @Test
    void testSavaGume() throws InterruptedException {
        WebElement autoOprema = webDriver.findElement(By.linkText("Auto i moto oprema"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(autoOprema).perform();
        Thread.sleep(100);
        webDriver.findElement(By.linkText("Sava")).click();
        Thread.sleep(4000);
        try {
            WebElement result = webDriver.findElement(By.partialLinkText("guma"));
            assertTrue(true);
        } catch (NoSuchElementException e) {
            fail("There should be 'guma' in results");
        }
    }
}
