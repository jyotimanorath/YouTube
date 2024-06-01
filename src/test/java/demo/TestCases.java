package demo;
import demo.utils.*;

import java.time.Duration;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {

    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setup() {
        System.out.println("Constructor: Driver");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Successfully Created Driver");

    }

    @Test(priority = 0,enabled = true, description = "Verify the url")
    public void testCase01() throws InterruptedException {
        System.out.println("Beginning Test Case 01");
        driver.get("https://www.youtube.com/");
        String expectedStr = "https://www.youtube.com/";
        String currentPage = driver.getCurrentUrl();
        Assert.assertTrue(currentPage.contains(expectedStr), "String did not contains the required text");
        System.out.println("The current page url contains youtube");
        Utilities.clickWrapper(driver, By.xpath("//a[@href='https://www.youtube.com/about/']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");
        String message = driver.findElement(By.xpath("(//section[@class='ytabout__content']/p)[1]")).getText();
        System.out.println(message);
        System.out.println("Ending Test Case 01");
    }

    @Test(priority = 1,enabled = true, description = "Check whether the movie is comedy or animation movie")
    public void testCase02() throws InterruptedException {
        System.out.println("Beginning Test Case 02");
        driver.get("https://www.youtube.com/");
        Utilities.clickWrapper(driver, By.xpath("//yt-formatted-string[text()='Movies']"));
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        // scroll till we found "Top selling" title
        Utilities.javaScroll(driver, By.xpath("//span[text()='Top selling']"));
         
        // locate extreme right button
        WebElement button = driver.findElement(By.xpath("(//div[@id='right-arrow']//descendant::button[@aria-label='Next'])[1]"));
        boolean result=button.isDisplayed();
         while (result) {
            try {
                // Check if the button is visible
                if (button.isDisplayed()) {
                   // Click the button
                    button.click();
                } else {
                    // If the button is not visible, exit the loop
                    break;
                }
            } catch (Exception e) {
                // Handle exceptions (e.g., element not found, timeout)
                e.printStackTrace();
                break;
            }
        }
        WebElement maturity = driver
                .findElement(By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]"));
        boolean status = Utilities.verifyMarkedForMature(driver,
                By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]"), "A");
        softAssert.assertTrue(status, "Contains A ");
        System.out.println(maturity.getText());

        WebElement typeOfMovies = driver.findElement(
                By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/a/span"));
         String[] text=(typeOfMovies.getText()).split("\\s");  
        String actualType=text[0].trim();     
        String expectedStr1="Comedy";
        String expectedStr2="Animation";
        System.out.println(actualType);
        softAssert.assertEquals(actualType, expectedStr1, "Actual string does not match expected1");
        softAssert.assertEquals(actualType, expectedStr2, "Actual string does not match expected2");
        System.out.println("This movie is comedy");
          
         System.out.println("Ending Test Case 02");
       
    }

    @Test(priority = 3,enabled=true, description = "Go to music and Print the name of the playlist ")
    public void testCase03() throws InterruptedException {
        System.out.println("Beginning Test Case 03");
        // driver.navigate().back();
        driver.get("https://www.youtube.com/");
        Utilities.clickWrapper(driver, By.xpath("(//yt-formatted-string[text()='Music'])[1]"));
        
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        // locate right button
        WebElement button = driver.findElement(By.xpath("(//button[@aria-label='Next'])[1]"));
        
        boolean status=button.isDisplayed();

        while (status) {
            try {
                // Check if the button is visible
                if (button.isDisplayed()) {
                    // Click the button
                    button.click();
                } else {
                    // If the button is not visible, exit the loop
                    break;
                }
            } catch (Exception e) {
                // Handle exceptions (e.g., element not found, timeout)
                e.printStackTrace();
                break;
            }
        }
         
        WebElement title = driver
                .findElement(By.xpath("(//h3[@class='style-scope ytd-compact-station-renderer'])[11]"));
        System.out.println("Playlist is==>" + title.getText());
         WebElement track = driver.findElement(By.xpath("(//*[@id='video-count-text'])[11]"));
        // Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        String trackNo = track.getText();
         String[] parts = trackNo.split(" ");
         String value=parts[0].toString();
         int intValue = Integer.parseInt(value);
         softAssert.assertTrue(intValue <= 50, "having track value 50");
        System.out.println("The value of the track is=>" + intValue);
        
        System.out.println("Ending Test Case 03");
    }

    @Test(priority = 4,enabled =true, description = "print the title and body of the 1st 3 Latest News Post")
    public void testCase04() throws InterruptedException {
        System.out.println("Beginning Test Case 04");
        // Go to YouTube
        driver.get("https://www.youtube.com/");
        // driver.navigate().back();
        Thread.sleep(3000);

        Utilities.clickWrapper(driver, By.xpath("//yt-formatted-string[text()='News']"));

        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement newsTitle = driver
                .findElement(By.xpath("//span[@id='title'][contains(text(),'Latest news posts')]"));
        System.out.println(newsTitle.getText());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");
        
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));        
 WebElement bodyContent = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']")));
        
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);

        for(int i = 1; i<=3; i++){
            System.out.println(Utilities.findElementAndPrintWE(driver, By.xpath("//div[@id='header']"), bodyContent, i));
            System.out.println(Utilities.findElementAndPrintWE(driver, By.xpath("//div[@id='body']"), bodyContent, i));
            Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        }

       int totalLikes = 0;
        List<WebElement> likes = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));
        for (int i = 0; i < 3; i++) {
            String likeStr = likes.get(i).getText();
            long likenumber = Utilities.convertToNumericValue(likeStr);
            totalLikes += likenumber;

        }
        System.out.println("total likes =" + totalLikes);
        System.out.println("Ending Test Case 04");
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Destroying Driver Instance");
        driver.close();
        driver.quit();
        System.out.println("Successfully Destroyed Driver");
    }
}
