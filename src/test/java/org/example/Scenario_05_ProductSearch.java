package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Scenario_05_ProductSearch {

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

        webDriver.get(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testSearchLaptop() {
        webDriver.findElement(By.id("js-site-search-input")).sendKeys("laptop");
        webDriver.findElement(By.className("js_search_button")).click();

        try {
            WebElement result = webDriver.findElement(By.partialLinkText("laptop"));
            assertTrue(true);
        } catch (NoSuchElementException e) {
            fail();
        }
    }



    @Test
    void testSearchNoResults() {
        String keysToSend = "xyzabc123nonexistent";
        webDriver.findElement(By.id("js-site-search-input")).sendKeys(keysToSend);
        webDriver.findElement(By.className("js_search_button")).click();

        String noResultsMessage = webDriver.findElement(By.xpath("/html/body/main/div[5]/div[2]/div[1]")).getText();
        assertEquals("0 artikala pronađenih za traženi pojam " + keysToSend, noResultsMessage);
    }

    @Test
    void testSearchSpecialCharacters() {
        String keysToSend = "!@#$%^&*()";
        webDriver.findElement(By.id("js-site-search-input")).sendKeys(keysToSend);
        webDriver.findElement(By.className("js_search_button")).click();

        assertFalse(webDriver.getCurrentUrl().contains("error"), "Should not redirect to error page");

        String noResultsMessage = webDriver.findElement(By.xpath("/html/body/main/div[5]/div[2]/div[1]")).getText();
        assertEquals("0 artikala pronađenih za traženi pojam " + keysToSend, noResultsMessage);
    }

    @Test
    void testEmptySearchSubmission() {
        webDriver.findElement(By.id("js-site-search-input")).sendKeys("");
        webDriver.findElement(By.className("js_search_button")).click();

        // Check what happens - might stay on same page, show all products, or show validation message
        String currentUrl = webDriver.getCurrentUrl();

        // Option 1: Check if validation message appears
        List<WebElement> validationMessages = webDriver.findElements(By.xpath("//*[contains(text(), 'Please enter') or contains(text(), 'required')]"));

        // Option 2: Check if it stays on current page or shows all products
        assertTrue(!validationMessages.isEmpty() || currentUrl.contains("search") || webDriver.findElements(By.className("product-item")).size() >= 1);
    }
}
