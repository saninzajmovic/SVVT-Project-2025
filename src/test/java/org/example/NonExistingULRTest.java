package org.example;

import org.junit.jupiter.api.*;
        import org.openqa.selenium.*;
        import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NonExistingULRTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/MS/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testNonExistingUrl() {
        String badUrl = "https://www.ekupi.ba/bs/ne-postoji-stranica-12345-xyz";
        driver.get(badUrl);

        String pageTitle = driver.getTitle();
        System.out.println("Page Title Found: " + pageTitle);

        assertEquals("×\n" +
                "404 Stranica nije pronađena", driver.findElement(By.xpath("/html/body/main/div[5]/div[1]/div")).getText());

        /*
        assertTrue(pageTitle.contains("nije pronađena") || pageTitle.contains("404") || pageTitle.contains("Greška"),
                "Page title did not indicate an error. Got: " + pageTitle);
        */

        try {
            WebElement homeButton = driver.findElement(By.xpath("//a[contains(text(), 'Početna') or contains(@class, 'btn-primary')]"));
            homeButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("Home button not found, clicking Logo instead.");
            WebElement logo = driver.findElement(By.xpath("/html/body/main/header/nav[1]/div/div[1]/div/div/div/a/img"));
            logo.click();
        }

        wait.until(ExpectedConditions.urlToBe("https://www.ekupi.ba/bs/"));
        assertEquals("https://www.ekupi.ba/bs/", driver.getCurrentUrl());
    }
}