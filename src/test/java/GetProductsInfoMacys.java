import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoMacys {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver = new HtmlUnitDriver();
        // mens clearance page
        //driver.get("http://www1.macys.com/shop/mens-clothing/sale-clearance?id=9559&edge=hybrid&cm_sp=us_hdr-_-homepage-_-9559_Apparel");
        // baby clearance
        //driver.get("http://www1.macys.com/shop/kids-clothes/baby-sale-clearance?id=64780&edge=hybrid&cm_sp=us_hdr-_-homepage-_-64780_Baby#!fn=sortBy%3DTOP_RATED%26productsPerPage%3D40&!qvp=iqvp");
        //home clearance
        driver.get("http://www1.macys.com/shop/holiday-lane/sale-clearance?id=32175&edge=hybrid&cm_sp=us_hdr-_-kids-baby-shop-all-baby-baby-sale-%26-clearance-_-http%3A%2F%2Fwww1.macys.com%2Fshop%2Fholiday-lane%2Fsale-clearance%3Fid%3D32175%26edge%3Dhybrid_Holiday-Lane-Clearance");
        //Thread.sleep(5000);
        List<WebElement> productElements = driver.findElements(By.cssSelector("div.productThumbnail"));
        //System.out.println(productElements.size());

        for (int i = 0 ; i < productElements.size() ; i ++){

            WebElement productDetails = productElements.get(i);
            WebElement imageElement = productDetails.findElement(By.cssSelector("img.thumbnailMainImage"));
            WebElement descElement = productDetails.findElement(By.cssSelector("div.shortDescription a"));
            String desc = descElement.getText();
            String targetUrl = descElement.getAttribute("href");
            String prices = productDetails.findElement(By.cssSelector("div.prices")).getText();
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