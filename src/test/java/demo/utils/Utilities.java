package demo.utils;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities {
    WebDriver driver;
    
    public static Boolean clickWrapper(WebDriver driver, By locator) {
        System.out.println("Clicking");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement button = driver.findElement(locator);
            button.click();
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }
        return success;
    }

    public static void javaScroll(WebDriver driver, By locator){
        try {
            JavascriptExecutor js=(JavascriptExecutor)driver;
            js.executeScript("arguments[0].scrollIntoView();",locator);
            Thread.sleep(3000);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception occured" + e.getMessage());
        }
    }

    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for the element to be clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        // Wait until element is visible after scrolling
        wait.until(ExpectedConditions.visibilityOf(element));
        
        // Click the element
        element.click();
        Thread.sleep(1000);
    }

   

    public static String findElementAndPrintWE(WebDriver driver, By locator, WebElement we, int elementNo){
        WebElement element = we.findElements(locator).get(elementNo);

        String txt = element.getText();
        return txt;
    }

    public static long convertToNumericValue(String value) {
        // Trim the string to remove any leading or trailing spaces
        value = value.trim().toUpperCase();

        // Check if the last character is non-numeric and determine the multiplier
        char lastChar = value.charAt(value.length() - 1);
        int multiplier = 1;
        switch (lastChar) {
            case 'K':
                multiplier = 1000;
                break;
            case 'M':
                multiplier = 1000000;
                break;
            case 'B':
                multiplier = 1000000000;
                break;
            default:
                // If the last character is numeric, parse the entire string
                if (Character.isDigit(lastChar)) {
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid format: " + value);
        }

        // Extract the numeric part before the last character
        String numericPart = value.substring(0, value.length() - 1);
        double number = Double.parseDouble(numericPart);

        // Calculate the final value
        return (long) (number * multiplier);
    }

    public static boolean verifyMarkedForMature(WebDriver driver,By locator,String mark){
        try {
            boolean status;
            WebElement maturitymark=driver.findElement(locator);
            if(maturitymark.getText().contains(mark)){
                status=true;
            }else{
                status=false;
            }
            return status;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //return false;
        }
        return false;
    }

}

