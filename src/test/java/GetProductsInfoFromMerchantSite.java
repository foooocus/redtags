
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
        Boolean createNew = true;
        //file = "C:\\Personal\\ecommerce\\redtags\\ProductDataCSV\\CuratedProducts_03-02-2014_09-38-962.csv";
        int maxPagesPerUrl = 2;

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
        String separator = "~";
        String colNames = "post_type" + separator + "post_status" + separator + "post_title" + separator + "post_content" + separator + "post_category" + separator + "product_title" + separator + "pro_logo" + separator + "regular_price" + separator + "clearance_price" + separator + "coupons" + separator + "target_urls" + separator + "extra_tag_line";
        utils.writeToCsvFile(writer, colNames, separator);

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
            driver.manage().window().maximize();
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
                paginationElement = "li a.next-set";
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
                paginationElement = "li.next";
                domain = "Nordstrom";
            } else if (domain.equals("hm")){
                productMainElement = "ul[id='list-products'] > li";
                specificImageElement = "div.image img:nth-of-type(1)";
                specificDescElement = "div a";
                specificPriceElement = "span.price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "li.next";
                domain = "H & M";
            } else if (domain.contains("urbanoutfitters")){
                productMainElement = "div.category-product";
                specificImageElement = "img";
                specificDescElement = "h2 a";
                specificPriceElement = "h3";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "span.category-pagination-pages a";
                domain = "Urban Outfitters";
            } else if (domain.contains("express")){
                productMainElement = "div.cat-thu-product";
                specificImageElement = "img.cat-thu-p-ima";
                specificDescElement = "li.cat-cat-prod-name a";
                specificPriceElement = "ul.cat-cat-prod-info";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "a[title='next']";
                domain = "Express";
            } else if (domain.contains("delias")){
                productMainElement = "div.directoryCell";
                specificImageElement = "div.thumbdiv img";
                specificDescElement = "div.thumbheader a";
                specificPriceElement = "div.thumbInfo div:nth-of-type(5)";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "div[id='topPagination'] td[align='right'] > a";
                domain = "Delia's";
            } else if (domain.contains("dkny")){
                productMainElement = "li.product";
                specificImageElement = "a.product-link img:nth-of-type(1)";
                specificDescElement = "a.product-name";
                specificPriceElement = "div.product-price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "li.next-page a";
                domain = "DKNY";
            }
            else if (domain.contains("blair") || domain.contains("bedfordfair")){
                productMainElement = "div.Quicklook";
                specificImageElement = "div.image-display img";
                specificDescElement = "div.display-text a";
                specificPriceElement = "div.display-price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "span.paginationBlockLink a";
                if (domain.contains("blair")){
                    domain = "Blair";
                } else if (domain.contains("bedfordfair")){
                    domain = "Bedford Fair";
                }
            } /* else if (domain.contains("saksfifthavenue")){
                productMainElement = "div.pa-product-large";
                specificImageElement = "img.pa-product-large";
                specificDescElement = "div.product-text a";
                specificPriceElement = "div.product-text";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "li a.next";
                domain = "Saks 5th Avenue";
            } */  else if (domain.contains("bloomingdales")){
                productMainElement = "div.productThumbnail";
                specificImageElement = "img.thumbnailImage";
                specificDescElement = "div.shortDescription a";
                specificPriceElement = "div.priceSale";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "li.nextArrow a";
                domain = "Bloomingdales";
            } else if (domain.contains("neimanmarcus")){
                productMainElement = "div.product";
                specificImageElement = "img";
                specificDescElement = "div.productname";
                specificPriceElement = "div.allpricing";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                paginationElement = "div.pagingSlide";
                domain = "Neiman Marcus";
            } else if (domain.contains("thelimited")){
                productMainElement = "div.product";
                specificImageElement = "img";
                specificDescElement = "div.name a";
                specificPriceElement = "div.price";
                imageAttribute = "src";
                targetUrlAttribute = "href";
                domain = "The Limited";
            } else if (domain.contains("oldnavy") || domain.contains("gap") || domain.contains("bananarepublic") || domain.contains("piperlime") || domain.contains("athleta")){
                productMainElement = "li.productCatItem";
                specificImageElement = "img";
                specificDescElement = "a.productItemName";
                specificPriceElement = "span.priceDisplay";
                imageAttribute = "productimagepath";
                targetUrlAttribute = "href";
                paginationElement = "a.paginatorForwardArrow";
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
                rowString = "post" + separator + "publish" + separator + desc + separator + " " + separator + category + separator + desc + separator + imageUrl + separator + bothPrices[0] + separator + bothPrices[1] + separator + coupon + separator + targetUrl + separator + domain;
                // write to CSV file if desc is not empty
                if (desc != ""){
                    if (!imageUrl.endsWith("gif")){
                        utils.writeToCsvFile(writer, rowString, separator);
                    }
                }
                // keep count of total products parsed
                totalProductsParsed++;
                Boolean needPage = true;
                if (j == noOfProductsDisplayed - 1 && paginationElement != ""){
                    try{
                    paginationArrow = driver.findElement(By.cssSelector(paginationElement));
                    } catch (Exception e){
                        System.out.print("********* Pagination End ******");
                        needPage = false;
                    }

                    if (needPage && maxNoOfPagesPerUrl != maxPagesPerUrl){
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