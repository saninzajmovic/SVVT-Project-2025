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

import static org.junit.jupiter.api.Assertions.*;

public class Scenario_04_Logout {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "E:\\_faks\\downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.ekupi.ba/";
    }

    @BeforeEach
    public void login() throws InterruptedException{
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        webDriver.findElement(By.className("login_link")).click();
        Thread.sleep(1000);

        String registerUrl = webDriver.getCurrentUrl();
        String mail = "test1@gmail.com";

        webDriver.findElement(By.id("j_username")).sendKeys(mail);
        webDriver.findElement(By.id("j_password")).sendKeys("ajNakafubrate!");

        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(1000);

        String welcomeMessage = webDriver.findElement(By.xpath("/html/body/main/header/nav[2]/div/div[2]/div[2]/ul[2]/li[2]")).getText();
        assertEquals("Dobrodošli Memo", welcomeMessage);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testLogout() throws InterruptedException {

        webDriver.findElement(By.linkText("Odjavi se")).click();

        String loginLink = webDriver.findElement(By.className("login_link")).getText();
        assertEquals("Prijava / Registracija", loginLink, "Should show register link");

        Thread.sleep(4000);
    }

    @Test
    void testMojProfilButtonAfterLogout() throws InterruptedException {

        webDriver.findElement(By.linkText("Odjavi se")).click();

        try {
            WebElement profileButton = webDriver.findElement(By.linkText("Moj profil"));
            fail("Profile button should not be visible when logged out");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }

        Thread.sleep(4000);
    }
}
