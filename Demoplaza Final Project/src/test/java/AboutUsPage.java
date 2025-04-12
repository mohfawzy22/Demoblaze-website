import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AboutUsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public AboutUsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void verifyAboutUsModal() {
        driver.findElement(By.linkText("About us")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoModal")));
        WebElement video = driver.findElement(By.tagName("video"));
        if (video.isDisplayed()) {
            System.out.println("🎥 About Us video loaded.");
        }
        driver.findElement(By.xpath("//div[@id='videoModal']//button[text()='Close']")).click();
    }
}