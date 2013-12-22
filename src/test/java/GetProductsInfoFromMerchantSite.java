import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoFromMerchantSite {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver = new HtmlUnitDriver();
        // ***********************Kohls****************************************
        // clearance page urls
        List<String> clearancePagesUrls = new ArrayList<String>();
        clearancePagesUrls.add("http://www.kohls.com/catalog/clearance-kids-shoes.jsp?CN=4294736457+4294732649+4294719777");
        // web elements
        String productMainElement = "ul[id='product-matrix'] li.product-small";
        String specificImageElement = "img.product-image";
        String specificDescElement = "h2 a";
        String specificPriceElement = "div.product-info";

        for (int i = 0; i < clearancePagesUrls.size(); i++){
        driver.get(clearancePagesUrls.get(i));
        //Thread.sleep(5000);
        List<WebElement> productElements = driver.findElements(By.cssSelector(productMainElement));
        System.out.println("");
        System.out.println("************************************************************************");
        System.out.println("Page URL: " + clearancePagesUrls.get(i));
        System.out.println("Page Size: " + productElements.size());

            for (int j = 0 ; j < productElements.size() ; j ++){

                WebElement productDetails = productElements.get(j);
                WebElement imageElement = productDetails.findElement(By.cssSelector(specificImageElement));
                WebElement descElement = productDetails.findElement(By.cssSelector(specificDescElement));
                String desc = descElement.getText();
                String targetUrl = descElement.getAttribute("href");
                String prices = productDetails.findElement(By.cssSelector(specificPriceElement)).getText();
                String imageUrl = imageElement.getAttribute("src");

                System.out.println("===================================================================================");
                System.out.println(j + ". Desc: " + desc);
                System.out.println(j + ". Prices: " + prices);
                System.out.println(j + ". Image URL: " + imageUrl);
                System.out.println(j + ". Target URL: " + targetUrl);
            }

        }
        driver.quit();


    }

}