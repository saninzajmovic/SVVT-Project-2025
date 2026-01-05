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

import static org.junit.jupiter.api.Assertions.*;

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

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        driver.manage().window().maximize();

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkoutGuest(){
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

        WebElement cartButton3 = driver.findElement(By.xpath("/html/body/main/div[5]/div[3]/div[5]/div/div/div[1]/button"));
        cartButton3.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    @Test
    public void testCheckoutWithEmptyCart() {
        driver.get("https://www.ekupi.ba/bs/cart");

        WebElement emptyMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Vaša košarica je prazna') or contains(@class, 'cart-empty')]")
        ));
        assertTrue(emptyMessage.isDisplayed(), "User should see 'Cart is empty' message");

        boolean isCheckoutButtonPresent = driver.findElements(By.cssSelector(".btn--continue-checkout")).size() > 0;

        if (isCheckoutButtonPresent) {
            WebElement btn = driver.findElement(By.cssSelector(".btn--continue-checkout"));
            String classes = btn.getAttribute("class");
            assertTrue(classes.contains("disabled") || !btn.isEnabled(),
                    "CRITICAL BUG: Checkout button is enabled even though cart is empty!");
        } else {
            System.out.println("Pass: Checkout button is correctly hidden.");
        }

        driver.get("https://www.ekupi.ba/bs/checkout/multi/login");

        String currentUrl = driver.getCurrentUrl();
        assertFalse(currentUrl.contains("/checkout/multi/"),
                "Security Fail: Site allowed access to Checkout page with an empty cart!");
    }

    @Test
    public void testAddressFormValidation() {
        // 1. Prepare State
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

        WebElement cartButton3 = driver.findElement(By.xpath("/html/body/main/div[5]/div[3]/div[5]/div/div/div[1]/button"));
        cartButton3.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        //*[@id="guest.email"]
        WebElement email = driver.findElement(By.xpath("//*[@id=\"guest.email\"]\n"));
        email.sendKeys("dummy@gmail.com");

        //*[@id="guest.confirm.email"]
        WebElement email2 = driver.findElement(By.xpath("//*[@id=\"guest.confirm.email\"]"));
        email2.sendKeys("dummy@gmail.com");

        WebElement guest = driver.findElement(By.xpath("//*[@id=\"guestForm\"]/div[3]/button"));
        guest.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. We are now on Address Page. Try to click "Next" (Dalje) immediately
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addressSubmit")));
        nextButton.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement negativeText = driver.findElement(By.xpath("/html/body/main/div[5]/div[1]/div"));
        assertEquals("×\n" +
                        "Pronađena je greška za adresu koju ste unijeli. Molimo Vas da provjerite greške i ponovno unesete Vašu adresu.",
                negativeText.getText(), "Greška");

    }

}
