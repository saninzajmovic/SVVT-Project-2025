package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Scenario_08_ShoppingCart {

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

        driver.get("https://www.ekupi.ba/bs/bf-2025-dodatni-benefit/ekupi-novogodisnji-slatki-paketic-savrsen-poklon-za-djecu/p/EK000786795");
        //driver.manage().window().fullscreen();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement cartButton = driver.findElement(By.xpath(" //*[@id=\"addToCartButton\"]"));
        cartButton.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement popup = driver.findElement(By.xpath("//*[@id=\"cboxLoadedContent\"]/div/div[3]/a[2]"));
        popup.click();

        WebElement cartButton2 = driver.findElement(By.xpath("/html/body/main/header/nav[2]/div/div[2]/div[2]/ul[1]/li/div/div/div[1]"));
        cartButton2.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkPrice(){
        WebElement price = driver.findElement(By.xpath("//*[@id=\"cartEntryActionForm\"]/div[3]/div[2]/div/div[1]/div[2]"));
        assertEquals("29,90 KM", price.getText());

        //*[@id="cartEntryActionForm"]/div[3]/div[2]/div/div[1]/div[2]
        //*[@id="addToCartButton"]
        //https://www.ekupi.ba/bs/bf-2025-dodatni-benefit/ekupi-novogodisnji-slatki-paketic-savrsen-poklon-za-djecu/p/EK000786795
    }

    @Test
    public void addToCart(){
        //WebElement amount = driver.findElement(By.xpath("//*[@id=\"quantity_0\"]"));
        //amount.clear();
        WebElement amount2 = driver.findElement(By.xpath("//*[@id=\"quantity_0\"]"));
        amount2.sendKeys(Keys.chord(Keys.CONTROL, "a"), "10");
        amount2.sendKeys(Keys.ENTER);

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        //WebElement testic = driver.findElement(By.xpath("//*[@id=\"updateCartForm0\"]/div[1]/span[2]/button"));
        //testic.click();

        // /html/body/main/div[5]/div[4]/div[1]/ul/table/tbody/tr/td/li[1]/div[4]/span[2]

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement price = driver.findElement(By.xpath("//*[@id=\"cartEntryActionForm\"]/div[3]/div[2]/div/div[1]/div[2]"));
        assertEquals("299,00 KM", price.getText());
    }

    @Test
    public void clearCartTest(){
        WebElement clearCart = driver.findElement(By.xpath("//*[@id=\"clearCartButton\"]"));
        clearCart.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement popupConfirmBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"clear-cart-popup-content\"]/div[2]/div[1]/a")
        ));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", popupConfirmBtn);

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement message = driver.findElement(By.xpath("/html/body/main/div[5]/div[4]/div[2]/div/p"));
        assertEquals("Vaša košarica je prazna!", message.getText());
    }

}
