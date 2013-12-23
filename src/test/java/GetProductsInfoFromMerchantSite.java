
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoFromMerchantSite {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver;
        int totalProductsParsed = 0;

        // clearance page urls
        List<String> clearancePagesUrls = new ArrayList<String>();
        clearancePagesUrls.add("http://www.kohls.com/catalog/clearance-kids-shoes.jsp?CN=4294736457+4294732649+4294719777");
        clearancePagesUrls.add("http://www.aeropostale.com/family/index.jsp?categoryId=10869011&cp=3534618.3534619.3534626.3595055");
        clearancePagesUrls.add("http://www1.macys.com/shop/holiday-lane/sale-clearance?id=32175&edge=hybrid&cm_sp=us_hdr-_-kids-baby-sho");
        clearancePagesUrls.add("http://www.kohls.com/catalog/clearance-kids-shoes.jsp?CN=4294736457+4294732649+4294719777");
        clearancePagesUrls.add("http://shop.nordstrom.com/c/all-womens-sale?dept=8000001&origin=topnav");

        String pageURL;

        for (int i = 0; i < clearancePagesUrls.size(); i++){
            // web elements
            String productMainElement = "";
            String specificImageElement = "";
            String specificDescElement = "";
            String specificPriceElement = "";
            String imageAttribute = "";
            String targetUrlAttribute = "";

            pageURL = "";
            pageURL = clearancePagesUrls.get(i);
            driver = new HtmlUnitDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            try{
            driver.get(pageURL);
            } catch (Exception e){
                System.out.println("==============================================================================");
                System.out.println("Exception in navigating URL: " + e);
                System.out.println("==============================================================================");
                continue;
            }
            //Thread.sleep(5000);
            String domain = pageURL.split("\\.")[1];
            if (domain.contains("macys")){
                productMainElement = "div.productThumbnail";
                specificImageElement = "img.thumbnailMainImage";
                specificDescElement = "div.shortDescription a";
                specificPriceElement = "div.prices";
                imageAttribute = "src";
                targetUrlAttribute = "href";
            } else if (domain.contains("kohls")){
                productMainElement = "ul[id='product-matrix'] li.product-small";
                specificImageElement = "img.product-image";
                specificDescElement = "h2 a";
                specificPriceElement = "div.product-info";
                imageAttribute = "src";
                targetUrlAttribute = "href";
            } else if (domain.contains("aeropostale")){
                productMainElement = "div[data-product-id]";
                specificImageElement = "div.details-image img";
                specificDescElement = "h4 a";
                specificPriceElement = "ul.price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
            } else if (domain.contains("nordstrom")){
                productMainElement = "div.fashion-item";
                specificImageElement = "img";
                specificDescElement = "a.title";
                specificPriceElement = "div.info";
                imageAttribute = "data-original";
                targetUrlAttribute = "href";
            } else {
                System.out.println("==============================================================================");
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Unrecognized Domain: " + domain);
                System.out.println("==============================================================================");
                continue;
            }

            List<WebElement> productElements = null;
            try{
            productElements = driver.findElements(By.cssSelector(productMainElement));
            } catch (Exception e){
                System.out.println("==============================================================================");
                System.out.println("Exception in getting products: " + e);
                System.out.println("==============================================================================");
                continue;
            }
            int noOfProductsDisplayed = productElements.size();
            System.out.println("");
            System.out.println("************************************************************************");
            System.out.println("Page URL: " + pageURL);
            System.out.println("Page Size: " + noOfProductsDisplayed);

            if (noOfProductsDisplayed == 0){
                System.out.println("%%%%%%%%% Restarting as the products parsed = zero %%%%%%%%%%%%%%%%%%%");
                continue;
            }

            for (int j = 0 ; j < noOfProductsDisplayed ; j ++){

                WebElement productDetails = productElements.get(j);
                WebElement imageElement = null;
                WebElement descElement = null;
                String prices = null;
                try {
                imageElement = productDetails.findElement(By.cssSelector(specificImageElement));
                descElement = productDetails.findElement(By.cssSelector(specificDescElement));
                prices = productDetails.findElement(By.cssSelector(specificPriceElement)).getText();
                } catch (Exception e){
                    System.out.println("==============================================================================");
                    System.out.println("Exception in getting product details: " + e);
                    System.out.println("==============================================================================");
                    continue;
                }
                String desc = descElement.getText();
                String targetUrl = descElement.getAttribute(targetUrlAttribute);
                String imageUrl = imageElement.getAttribute(imageAttribute);

                System.out.println("===================================================================================");
                System.out.println(j + ". Desc: " + desc);
                System.out.println(j + ". Prices: " + prices);
                System.out.println(j + ". Image URL: " + imageUrl);
                System.out.println(j + ". Target URL: " + targetUrl);
                totalProductsParsed++;
            }
            driver.quit();
        }

    System.out.println("****************************************");
    System.out.println("TOTAL PRODUCTS: " + totalProductsParsed);
    System.out.println("****************************************");

    }

}