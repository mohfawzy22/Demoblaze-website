import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ContactPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void sendContactForm(String email, String name, String message) {
        driver.findElement(By.linkText("Contact")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));
        driver.findElement(By.id("recipient-email")).sendKeys(email);
        driver.findElement(By.id("recipient-name")).sendKeys(name);
        driver.findElement(By.id("message-text")).sendKeys(message);
        driver.findElement(By.xpath("//button[text()='Send message']")).click();
    }
}