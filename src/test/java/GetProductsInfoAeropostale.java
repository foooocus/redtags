import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoAeropostale {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver = new HtmlUnitDriver();
        // mens clearance page
        driver.get("http://www.aeropostale.com/family/index.jsp?categoryId=10869011&cp=3534618.3534619.3534626.3595055");
        //Thread.sleep(5000);
        List<WebElement> productElements = driver.findElements(By.cssSelector("div[data-product-id]"));
        //System.out.println(productElements.size());

        for (int i = 0 ; i < productElements.size() ; i ++){

            WebElement productDetails = productElements.get(i);
            WebElement imageElement = productDetails.findElement(By.cssSelector("div.details-image img"));
            String imageUrl = imageElement.getAttribute("src");
            //String title = imageElement.getAttribute("title");
            //String targetProductUrl = productDetails.findElement(By.cssSelector("h2 a")).getAttribute("href");
            String productInfo = productDetails.findElement(By.cssSelector("ul.price")).getText();
            System.out.println("_____________________________________________________________________________________________");
            System.out.println(i + ". Product Info: " + productInfo);
            System.out.println(i + ". Image URL: " + imageUrl);
            //System.out.println(i + ". Title: " +title);
            //System.out.println(i + ". Target Product URL: " +targetProductUrl);
        }


        driver.quit();


    }

}