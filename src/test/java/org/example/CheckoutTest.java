package org.example;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutTest {

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

        WebElement cookie = driver.findElement(By.xpath("//*[@id=\"js-cookie-notification\"]/div/button[2]"));
        cookie.click();

        WebElement popup = driver.findElement(By.xpath("//*[@id=\"cboxLoadedContent\"]/div/div[3]/a[2]"));
        popup.click();

        WebElement cartButton2 = driver.findElement(By.xpath("/html/body/main/header/nav[2]/div/div[2]/div[2]/ul[1]/li/div/div/div[1]"));
        cartButton2.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement cartButton3 = driver.findElement(By.xpath("/html/body/main/div[5]/div[3]/div[5]/div/div/div[1]/button"));
        cartButton3.click();

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
    public void checkoutGuest(){

        //*[@id="guest.email"]
        WebElement email = driver.findElement(By.xpath("//*[@id=\"guest.email\"]\n"));
        email.sendKeys("dummy@gmail.com");

        //*[@id="guest.confirm.email"]
        WebElement email2 = driver.findElement(By.xpath("//*[@id=\"guest.confirm.email\"]"));
        email2.sendKeys("dummy@gmail.com");

        //*[@id="guestForm"]/div[3]/button
        WebElement guest = driver.findElement(By.xpath("//*[@id=\"guestForm\"]/div[3]/button"));
        guest.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = driver.getCurrentUrl();
        assertEquals("https://www.ekupi.ba/bs/checkout/multi/delivery-address/add", url, "Not expected url");

        WebElement price = driver.findElement(By.xpath("//*[@id=\"sideTotal\"]/div/span"));
        assertEquals("29,90 KM", price.getText(),"not equal");

        //*[@id="sideTotal"]/div

    }

}
