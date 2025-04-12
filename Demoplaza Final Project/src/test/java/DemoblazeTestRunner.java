import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DemoblazeTestRunner {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.demoblaze.com/");
            driver.manage().window().maximize();

            // Login
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();
            Thread.sleep(1000); // Modal wait
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))).sendKeys("MFawzy");
            driver.findElement(By.id("loginpassword")).sendKeys("autoPass2050");
            driver.findElement(By.xpath("//button[text()='Log in']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

            // Products to add (titles must match links exactly)
            List<String> products = Arrays.asList(
                    "Samsung galaxy s6",
                    "Nokia lumia 1520",
                    "Nexus 6",
                    "Sony vaio i5",
                    "Sony vaio i7",
                    "MacBook air",
                    "MacBook Pro",
                    "Dell i7 8gb",
                    "Apple monitor 24",
                    "ASUS Full HD"
            );

            for (String product : products) {
                // Go to Home page
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Home ']"))).click();
                Thread.sleep(1000); // Allow reload

                // Category-specific navigation
                if (product.contains("Samsung") || product.contains("Nokia") || product.contains("Nexus")) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();
                } else if (product.contains("vaio") || product.contains("MacBook") || product.contains("Dell")) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops"))).click();
                } else {
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Monitors"))).click();
                }

                Thread.sleep(1000); // Wait for category products to load
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText(product))).click();

                // Add to cart
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
            }
            // Contact Us
            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipient-email"))).sendKeys("mohamedfawzy3750@gmail.com");
                driver.findElement(By.id("recipient-name")).sendKeys("Mohamed Omar");
                driver.findElement(By.id("message-text")).sendKeys("i have a problem");
                driver.findElement(By.xpath("//button[text()='Send message']")).click();
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                System.out.println("✅ Contact form submitted successfully");
            } catch (Exception e) {
                System.out.println("❌ Failed to test 'Contact us': " + e.getMessage());
            }
            // About Us
            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About us"))).click();
                WebElement aboutModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoModal")));

                if (aboutModal.isDisplayed()) {
                    System.out.println("✅ 'About us' modal displayed");

                    // Check if video is playing
                    Boolean isVideoPlaying = (Boolean) ((JavascriptExecutor) driver).executeScript(
                            "var video = document.querySelector('#videoModal video');" +
                                    "return video && !video.paused && !video.ended && video.readyState > 2;"
                    );

                    if (isVideoPlaying != null && isVideoPlaying) {
                        System.out.println("✅ Video is playing in 'About us' modal");
                    } else {
                        System.out.println("❌ Video is not playing or failed to load");
                    }

                    driver.findElement(By.xpath("//div[@id='videoModal']//button[text()='Close']")).click();
                } else {
                    System.out.println("❌ 'About us' modal not visible");
                }
            } catch (Exception e) {
                System.out.println("❌ Failed to test 'About us': " + e.getMessage());
            }
            // Go to Cart
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

            // Place Order
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();

            // Fill form
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Mohamed Omar");
            driver.findElement(By.id("country")).sendKeys("Egypt");
            driver.findElement(By.id("city")).sendKeys("Cairo");
            driver.findElement(By.id("card")).sendKeys("1234567890123456");
            driver.findElement(By.id("month")).sendKeys("April");
            driver.findElement(By.id("year")).sendKeys("2025");

            // Confirm Purchase
            driver.findElement(By.xpath("//button[text()='Purchase']")).click();

            // Confirmation
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sweet-alert")));
            System.out.println("✅ Purchase Confirmation:\n" + confirmation.getText());

            driver.findElement(By.xpath("//button[text()='OK']")).click();
            System.out.println("✅ 10 Products Added & Purchased Successfully!");
            System.out.println("All test cases  run successfully");
            System.out.println("thank you for using Demoblaze Website");
            System.out.println("we are waiting you for next purchase");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
