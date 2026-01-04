package org.example;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductDetailsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/MS/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get("https://www.ekupi.ba/bs/racunari/racunari-i-periferija/racunarske-komponente/gaming-komponente/gaming-graficke-kartice/asus-graficka-kartica-prime-nvidia-geforce-rt-x5070-o12g-12gb-gddr7-192-bit3x-dp-1x-hdmi/p/EK000694785");
        //driver.manage().window().fullscreen();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkPrice(){
        WebElement cijena = driver.findElement(By.xpath("/html/body/main/div[5]/div[2]/div[3]/div/div/div[1]/div[2]/div[2]/dl/dd"));

        assertEquals("1.999,00 KM", cijena.getText());
    }

    @Test
    public void zoomTest(){
        WebElement slika = driver.findElement(By.xpath("/html/body/main/div[5]/div[2]/div[1]/div/div/div[3]/div[1]/div/div[1]/div/div/img"));

        Actions action = new Actions(driver);
        action.moveToElement(slika).perform();

        try{
            Thread.sleep(3000);
            WebElement zoomLens = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("lazyOwl")
            ));
            assertTrue(zoomLens.isDisplayed() || zoomLens.isEnabled(), "Zoom lens did not appear on hover");
            System.out.println("Zoom functionality verified.");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void specificationsTest(){
        WebElement specs = driver.findElement(By.xpath("//*[@id=\"accessibletabsnavigation0-1\"]"));

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 200)");
        specs.click();

        WebElement garancija = driver.findElement(By.xpath("//*[@id=\"tabDetailsId\"]/div/div[4]/div/div/div/div/div/table[1]/tbody/tr/td[2]"));
        assertEquals("3 god", garancija.getText());

        WebElement model = driver.findElement(By.xpath("//*[@id=\"tabDetailsId\"]/div/div[4]/div/div/div/div/div/table[5]/tbody/tr/td[2]"));
        assertEquals("GeForce RTX 5070", model.getText());


        //*[@id="tabDetailsId"]/div/div[4]/div/div/div/div/div/table[5]/tbody/tr/td[2]

        //*[@id="tabDetailsId"]/div/div[4]/div/div/div/div/div/table[1]/tbody/tr/td[2]
    }

}