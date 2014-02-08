import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mgdharmadas on 12/21/13.
 */
public class GetProductsInfoAeropostale {

    public static void main(String[] args) throws InterruptedException {
        Utils utils = new Utils();
        String priceString = "Steve Madden Regulabelearance: $123";
        //String priceString = "Steve Madden 'Alexxia' Bootie Regular: $145 Was: $129.95 Now: $87.0633% OFF (7)";
        System.out.println(">>>>>>>>>>>>>>>>> Regular: " + utils.returnPrices(priceString)[0]);
        System.out.println(">>>>>>>>>>>>>>>>> Clearance: " + utils.returnPrices(priceString)[1]);


    }




}

