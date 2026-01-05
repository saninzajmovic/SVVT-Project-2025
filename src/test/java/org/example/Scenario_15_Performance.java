package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Scenario_15_Performance {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final long MAX_LOAD_TIME_MS = 5000;

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
    public void testHomepageLoadTime() {
        checkPagePerformance("https://www.ekupi.ba/bs/");
    }

    @Test
    public void testProductPageLoadTime() {
        // Use a known heavy product page
        checkPagePerformance("https://www.ekupi.ba/bs/Elektronika/Mobiteli-i-dodaci/Mobiteli/Apple-iPhone-15-128GB-Black%2C-mobitel/p/EK000557438");
    }

    @Test
    public void testSearchResultsLoadTime() {
        // Direct link to search results
        checkPagePerformance("https://www.ekupi.ba/bs/search/?text=laptop");
    }

    // --- HELPER METHOD ---
    private void checkPagePerformance(String url) {
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));

        Long loadTime = (Long) js.executeScript(
                "var perfData = window.performance.timing;" +
                        "return perfData.loadEventEnd - perfData.navigationStart;"
        );

        System.out.println("URL: " + url + " | Load Time: " + loadTime + "ms");

        if (loadTime > MAX_LOAD_TIME_MS) {
            System.err.println("WARNING: Page took too long to load! (" + loadTime + "ms)");
        }

        // Uncomment this if you want the test to actively FAIL on slow pages
         assertTrue(loadTime <= MAX_LOAD_TIME_MS,
            "Page load time (" + loadTime + "ms) exceeded budget (" + MAX_LOAD_TIME_MS + "ms)");
    }
}