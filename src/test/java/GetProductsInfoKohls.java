import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoKohls {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver = new HtmlUnitDriver();
        // mens clearance page
        //driver.get("http://www.kohls.com/catalog/mens-clearance-clothing.jsp?CN=4294723349+4294736457+4294719810&icid=clmf|mfs4");
        //kids shoes clearance
        driver.get("http://www.kohls.com/catalog/clearance-kids-shoes.jsp?CN=4294736457+4294732649+4294719777");
        //Thread.sleep(5000);
        List<WebElement> productElements = driver.findElements(By.cssSelector("ul[id='product-matrix'] li.product-small"));
        System.out.println(productElements.size());

        for (int i = 0 ; i < productElements.size() ; i ++){

            WebElement productDetails = productElements.get(i);
            WebElement imageElement = productDetails.findElement(By.cssSelector("img.product-image"));
            WebElement descElement = productDetails.findElement(By.cssSelector("h2 a"));
            String desc = descElement.getText();
            String targetUrl = descElement.getAttribute("href");
            String prices = productDetails.findElement(By.cssSelector("div.product-info")).getText();
            String imageUrl = imageElement.getAttribute("src");

            System.out.println("===================================================================================");
            System.out.println(i + ". Desc: " + desc);
            System.out.println(i + ". Prices: " + prices);
            System.out.println(i + ". Image URL: " + imageUrl);
            System.out.println(i + ". Target URL: " + targetUrl);
        }


        driver.quit();


    }

}