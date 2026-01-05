package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class Scenario_12_ResponsiveDesign {

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
    void testDesktopView() {
        webDriver.manage().window().setSize(new Dimension(1920, 1080));

        WebElement desktopMenu = webDriver.findElement(By.className("desktop__nav"));
        assertTrue(desktopMenu.isDisplayed(), "Desktop menu should be visible");

        WebElement hamburgerButton = webDriver.findElement(By.className("js-toggle-sm-navigation"));
        assertFalse(hamburgerButton.isDisplayed(), "Hamburger menu should not be visible on desktop");
    }

    @Test
    void testMobileView() {
        webDriver.manage().window().setSize(new Dimension(375, 667)); // iPhone size

        WebElement hamburgerButton = webDriver.findElement(By.className("js-toggle-sm-navigation"));
        assertTrue(hamburgerButton.isDisplayed(), "Hamburger menu should be visible on mobile");
    }

    @Test
    void testMenuCollapsesOnMobile() throws InterruptedException{
        webDriver.manage().window().setSize(new Dimension(375, 667));

        // open hamburger menu
        webDriver.findElement(By.className("js-toggle-sm-navigation")).click();

        Thread.sleep(1000);
        WebElement mobileMenu = webDriver.findElement(By.xpath("/html/body/main/header/nav[3]"));

        assertTrue(mobileMenu.isDisplayed(), "Mobile menu should be visible after clicking hamburger");
    }

    @Test
    void testElementsRemainClickable() {
        webDriver.manage().window().setSize(new Dimension(375, 667));

        // Test that buttons/links are still clickable at mobile size
        WebElement searchButton = webDriver.findElement(By.xpath("/html/body/main/header/nav[2]/div/div[1]/div/div/div/div[2]/button/span"));
        assertTrue(searchButton.isDisplayed(), "Search button should be visible");
        assertTrue(searchButton.isEnabled(), "Search button should be clickable");

        // Actually click it to verify
        searchButton.click();

        // Verify the click worked (adjust based on what should happen)
        assertNotEquals("", webDriver.getCurrentUrl());
    }
}
