package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Scenario_06_ProductFilteringSorting {

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

        driver.get("https://www.ekupi.ba/bs/");
        driver.manage().window().fullscreen();

        WebElement toHover = driver.findElement(By.xpath("/html/body/main/header/nav[3]/div/ul[3]/li[3]/span[1]/a"));

        Actions action = new Actions(driver);

        action.moveToElement(toHover).perform();

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement RacKomp = driver.findElement(By.xpath("/html/body/main/header/nav[3]/div/ul[3]/li[3]/div/div/div[2]/ul/li[4]/a"));

        RacKomp.click();

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.manage().window().fullscreen();

        WebElement text = driver.findElement(By.xpath("/html/body/main/div[5]/div[1]/h1"));

        assertEquals("Računarske komponente", text.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void FilterByPriceRange(){
        WebElement lowPrice = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[3]/div[2]/ul/li[2]/div/div[1]/input"));
        lowPrice.clear();
        lowPrice.sendKeys("67");

        WebElement highPrice = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[3]/div[2]/ul/li[2]/div/div[2]/input"));
        highPrice.clear();
        highPrice.sendKeys("99");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[3]/div[2]/div/span/a"));
        submit.click();

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.manage().window().fullscreen();

        WebElement finalText = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[1]/div[2]/ul/li/form"));
        String actualText = finalText.getText().trim();
        assertEquals("Cijena Od 67 KM Do 99 KM", actualText);
    }


    @Test
    public void sortByPriceHigh(){
        //*[@id="sortOptions1"]
        WebElement select = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        select.click();

        Select option = new Select(select);
        option.selectByValue("price-desc");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement newSelectElement = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        Select newDropdown = new Select(newSelectElement);
        String actualText = newDropdown.getFirstSelectedOption().getText().trim();
        assertEquals("CIJENA (PRVO NAJVIŠA)", actualText);
    }

    @Test
    public void sortByPriceLow(){
        WebElement select = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        select.click();

        Select option = new Select(select);
        option.selectByValue("price-asc");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement newSelectElement = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        Select newDropdown = new Select(newSelectElement);
        String actualText = newDropdown.getFirstSelectedOption().getText().trim();
        assertEquals("CIJENA (PRVO NAJNIŽA)", actualText);
    }

    @Test
    public void sortByRatings(){
        WebElement select = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        select.click();

        Select option = new Select(select);
        option.selectByValue("topRated");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement newSelectElement = driver.findElement(By.xpath("//*[@id=\"sortOptions1\"]"));
        Select newDropdown = new Select(newSelectElement);
        String actualText = newDropdown.getFirstSelectedOption().getText().trim();
        assertEquals("NAJVIŠE OCJENE", actualText);
    }

    @Test
    public void sortByBrand(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[4]/div[2]/ul[1]/li[1]/form/label/span/span[1]"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 100)");

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        //driver.findElement(By.xpath())
        //((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");

        element.click();


        WebElement finalText = driver.findElement(By.xpath("//*[@id=\"product-facet\"]/div[1]/div[2]/ul/li/form"));
        String actualText = finalText.getText().trim();
        assertEquals("AMD", actualText);

    }

    //*[@id="product-facet"]/div[4]/div[2]/ul[1]/li[1]/form/label/span/span[1]
}