
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoFromMerchantSite {

    public static void main(String[] args) throws InterruptedException {

        Utils utils = new Utils();
        String file = "";
        Boolean createNew = false;
        file = "C:\\Personal\\ecommerce\\redtags\\ProductDataCSV\\CuratedProducts_03-02-2014_09-38-962.csv";
        int maxPagesPerUrl = 1000;

        String inpurUrlCsv = "C:\\Personal\\ecommerce\\redtags\\UrlCsvFile\\pageUrls.csv";

        System.setProperty("webdriver.chrome.driver", "C:/Users/mgdharmadas/IdeaProjects/chromedriver.exe");
        WebDriver driver;
        int totalProductsParsed = 0;
        // clearance page urls
        CSVReader reader = utils.createFileReader(inpurUrlCsv);
        List<String[]> clearancePagesUrls = utils.getAllRowsFromCsvFile(reader);

        String pageURL;
        String category, coupon;
        String domain;

        // CSV write column headers
        if (createNew){
            file = utils.createFileName("C:\\Personal\\ecommerce\\redtags\\ProductDataCSV\\");
        }
        CSVWriter writer = utils.createFileWriter(file);
        String colNames = "post_type" + "#" + "post_status" + "#" + "post_title" + "#" + "post_content" + "#" + "post_category" + "#" + "product_title" + "#" + "pro_logo" + "#" + "regular_price" + "#" + "clearance_price" + "#" + "coupons" + "#" + "target_urls" + "#" + "extra_tag_line";
        utils.writeToCsvFile(writer, colNames);

        for (int i = 0; i < clearancePagesUrls.size(); i++){
            // web elements
            String productMainElement = "";
            String specificImageElement = "";
            String specificDescElement = "";
            String specificPriceElement = "";
            String paginationElement = "";
            String imageAttribute = "";
            String targetUrlAttribute = "";

            pageURL = "";
            category = "";
            coupon = "";
            pageURL = clearancePagesUrls.get(i)[0];
            category = clearancePagesUrls.get(i)[1];
            coupon = clearancePagesUrls.get(i)[2];
            //driver = new HtmlUnitDriver();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            try{
            driver.get(pageURL);
            } catch (Exception e){
                System.out.println("==============================================================================");
                System.out.println("Exception in navigating URL: " + e);
                System.out.println("==============================================================================");
                continue;
            }
            Thread.sleep(3000);
            domain = "";
            domain = pageURL.split("\\.")[1];
            if (domain.contains("macys")){
                productMainElement = "div.productThumbnail";
                specificImageElement = "img.thumbnailMainImage";
                specificDescElement = "div.shortDescription a";
                specificPriceElement = "div.prices";
                paginationElement = "div.pagination a.arrowRight";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                domain = "Macy's";
            } else if (domain.contains("kohls")){
                productMainElement = "ul[id='product-matrix'] li.product-small";
                specificImageElement = "img.product-image";
                specificDescElement = "h2 a";
                specificPriceElement = "div.product-info";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                domain = "Kohl's";
            } else if (domain.contains("aeropostale")){
                productMainElement = "div[data-product-id]";
                specificImageElement = "div.details-image img";
                specificDescElement = "h4 a";
                specificPriceElement = "ul.price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                domain = "Aeropostale";
            } else if (domain.contains("nordstrom")){
                productMainElement = "div.fashion-item";
                specificImageElement = "img";
                specificDescElement = "a.title";
                specificPriceElement = "div.info";
                imageAttribute = "data-original";
                targetUrlAttribute = "href";
                domain = "Nordstrom";
            } else if (domain.contains("oldnavy") || domain.contains("gap") || domain.contains("bananarepublic") || domain.contains("piperlime") || domain.contains("athleta")){
                productMainElement = "li.productCatItem";
                specificImageElement = "img";
                specificDescElement = "a.productItemName";
                specificPriceElement = "span.priceDisplay";
                imageAttribute = "productimagepath";
                targetUrlAttribute = "href";
                if (domain.contains("oldnavy")){
                    domain = "Old Navy";
                } else if (domain.contains("gap")){
                    domain = "Gap";
                } else if (domain.contains("bananarepublic")){
                    domain = "Banana Republic";
                } else if (domain.contains("piperlime")){
                    domain = "PiperLime";
                } else if (domain.contains("athleta")){
                    domain = "Athleta";
                }
            } else {
                System.out.println("==============================================================================");
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Unrecognized Domain: " + domain);
                System.out.println("==============================================================================");
                continue;
            }


            String rowString;
            String prices;
            String[] bothPrices;

            List<WebElement> productElements = null;
            productElements = getAllDisplayedProductElements(driver, productMainElement);
            int noOfProductsDisplayed = productElements.size();

            System.out.println("");
            System.out.println("************************************************************************");
            System.out.println("Page URL: " + pageURL);
            System.out.println("Page Size: " + noOfProductsDisplayed);

            if (noOfProductsDisplayed == 0){
                System.out.println("%%%%%%%%% Restarting as the products parsed = zero %%%%%%%%%%%%%%%%%%%");
                continue;
            }

            WebElement paginationArrow = null;
            int maxNoOfPagesPerUrl = 1;

            for (int j = 0 ; j < noOfProductsDisplayed ; j ++){

                WebElement productDetails = productElements.get(j);
                WebElement imageElement = null;
                WebElement descElement = null;
                prices = null;
                bothPrices = null;
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

                bothPrices = utils.returnPrices(prices);
                rowString = "post" + "#" + "publish" + "#" + desc + "#" + " " + "#" + category + "#" + desc + "#" + imageUrl + "#" + bothPrices[0] + "#" + bothPrices[1] + "#" + coupon + "#" + targetUrl + "#" + domain;
                // write to CSV file if desc is not empty
                if (desc != ""){
                    utils.writeToCsvFile(writer, rowString);
                }
                // keep count of total products parsed
                totalProductsParsed++;
                Boolean needPage = true;
                if (paginationElement != ""){
                    try{
                    paginationArrow = driver.findElement(By.cssSelector(paginationElement));
                    } catch (Exception e){
                        System.out.print("********* Pagination End ******");
                        needPage = false;
                    }

                    if (j == noOfProductsDisplayed - 1 && needPage && maxNoOfPagesPerUrl != maxPagesPerUrl){
                        if (paginationArrow.isEnabled()){
                            maxNoOfPagesPerUrl++;
                            paginationArrow.click();
                            Thread.sleep(5000);
                            productElements = getAllDisplayedProductElements(driver, productMainElement);
                            noOfProductsDisplayed = productElements.size();
                            j = 0;
                        }
                    }
                }

            }
            // close browser for that URL
            driver.quit();
        }
        // Close file once all URLs are parsed
        utils.closeWriter(writer);

    System.out.println("****************************************");
    System.out.println("TOTAL PRODUCTS: " + totalProductsParsed);
    System.out.println("****************************************");

    }

    private static List<WebElement> getAllDisplayedProductElements(WebDriver driver, String productMainElement) {
        List<WebElement> productElements = null;
        try{
            productElements = driver.findElements(By.cssSelector(productMainElement));
        } catch (Exception e){
            System.out.println("==============================================================================");
            System.out.println("Exception in getting products: " + e);
            System.out.println("==============================================================================");

        }

        return productElements;
    }



}