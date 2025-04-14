import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BaseTest {

    public void goToCartAndPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
    }

    public void fillOrderFormAndPurchase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Mohamed Omar");
        driver.findElement(By.id("country")).sendKeys("QAland");
        driver.findElement(By.id("city")).sendKeys("Cairo");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("April");
        driver.findElement(By.id("year")).sendKeys("2025");

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sweet-alert")));
        System.out.println("✅ Order Confirmation: \n" + confirmation.getText());

        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }
}