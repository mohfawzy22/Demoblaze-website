import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DemoblazeTestNG {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
    }

    @Test(priority = 1)
    public void loginTest() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))).sendKeys("MFawzy");
            driver.findElement(By.id("loginpassword")).sendKeys("autoPass2050");
            driver.findElement(By.xpath("//button[text()='Log in']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            System.out.println(" Login Test Passed");
        } catch (Exception e) {
            Assert.fail(" Login Test Failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void addProductsToCart() {
        List<String> products = Arrays.asList(
                "Samsung galaxy s6", "Nokia lumia 1520", "Nexus 6",
                "Sony vaio i5", "Sony vaio i7", "MacBook air",
                "MacBook Pro", "Dell i7 8gb", "Apple monitor 24", "ASUS Full HD"
        );

        for (String product : products) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Home ']"))).click();
                Thread.sleep(1000);

                if (product.contains("Samsung") || product.contains("Nokia") || product.contains("Nexus")) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();
                } else if (product.contains("vaio") || product.contains("MacBook") || product.contains("Dell")) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops"))).click();
                } else {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Monitors"))).click();
                }

                Thread.sleep(1000);
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText(product))).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                System.out.println(" Added to cart: " + product);
            } catch (Exception e) {
                Assert.fail(" Failed to add: " + product + " - " + e.getMessage());
            }
        }
    }

    @Test(priority = 3)
    public void testContactUs() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipient-email"))).sendKeys("mohamedfawzy3750@gmail.com");
            driver.findElement(By.id("recipient-name")).sendKeys("Mohamed Omar");
            driver.findElement(By.id("message-text")).sendKeys("i have a problem");
            driver.findElement(By.xpath("//button[text()='Send message']")).click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            System.out.println(" Contact Form Test Passed");
        } catch (Exception e) {
            Assert.fail(" Contact Us Test Failed: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testAboutUsVideo() {
        SoftAssert softAssert = new SoftAssert(); // Allows the test to fail without breaking the flow

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About us"))).click();
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoModal")));

            if (modal.isDisplayed()) {
                System.out.println(" 'About us' modal displayed");

                // Simulate failure: check for wrong video element ID
                Boolean isVideoPlaying = (Boolean) ((JavascriptExecutor) driver).executeScript(
                        "var video = document.querySelector('#videoModal video');" +
                                "return video && !video.paused && !video.ended && video.readyState > 2;"
                );

                if (isVideoPlaying != null && isVideoPlaying) {
                    System.out.println(" Video is playing");
                } else {
                    System.out.println(" Video is NOT playing or failed to load");
                    softAssert.fail("Simulated Failure: Video not playing or failed to load");
                }

                driver.findElement(By.xpath("//div[@id='videoModal']//button[text()='Close']")).click();
            } else {
                System.out.println(" Modal is not visible");
                softAssert.fail("Simulated Failure: About Us modal not visible");
            }

        } catch (Exception e) {
            System.out.println(" About Us Test Failed: " + e.getMessage());
            softAssert.fail("Exception thrown: " + e.getMessage());
        }

        softAssert.assertAll(); // Fails the test, but doesn't stop suite
    }

    @Test(priority = 5)
    public void completeOrder() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Mohamed Omar");
            driver.findElement(By.id("country")).sendKeys("Egypt");
            driver.findElement(By.id("city")).sendKeys("Cairo");
            driver.findElement(By.id("card")).sendKeys("1234567890123456");
            driver.findElement(By.id("month")).sendKeys("April");
            driver.findElement(By.id("year")).sendKeys("2025");
            driver.findElement(By.xpath("//button[text()='Purchase']")).click();

            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sweet-alert")));
            System.out.println(" Order Confirmation:\n" + confirmation.getText());

            driver.findElement(By.xpath("//button[text()='OK']")).click();
        } catch (Exception e) {
            Assert.fail(" Order Process Failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
