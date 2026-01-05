package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

class Scenario_01_HomepageAccessibility {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", "E:\\_faks\\downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.ekupi.ba/";

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
    void testHttps() {
        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("https", currentUrl.substring(0, 5), "Url should be https");
    }

    @Test
    void testBaseUrl() {
        assertEquals(baseUrl, webDriver.getCurrentUrl(), "https://www.ekupi.ba/ should have been opened");
    }

    @Test
    void testLogo() {
        WebElement logo = webDriver.findElement(
                By.xpath("/html/body/main/header/nav[1]/div/div[1]/div/div/div/a/img")
        );
        assertTrue(logo.isDisplayed(), "Logo should be visible");
    }

    @Test
    void testLogoTitle() {
        WebElement logo = webDriver.findElement(
                By.xpath("/html/body/main/header/nav[1]/div/div[1]/div/div/div/a/img")
        );

        assertEquals("eKupi logo", logo.getDomAttribute("title"), "Logo should have title attribute 'eKupi logo'");
    }

}