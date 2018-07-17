/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sco.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统公告设置Controller
 *
 * @author 段文昌
 * @version 2015-11-07
 */
public class SeleniumDriver {

    private static WebDriver driver;
    public static String main() {

//        System.setProperty("webdriver.chrome.driver","");

        final String dir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", dir+"/lib/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("http://www.vegnet.com.cn/market/233.html");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String url = driver.getCurrentUrl();
        System.out.println(url);
        while (!url.equals("http://www.vegnet.com.cn/market/233.html")){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<WebElement> items = driver.findElements(By.xpath("//ul[@class='pri_tw market_tw']//li//a"));
        System.out.println(items.size());
        items.get(0).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String table_html ="";
        try{
            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            WebElement table_e = driver.findElement(By.xpath("//div[@class='jxs_list price_l']"));
            String table_text = table_e.getText();
            table_html = table_e.getAttribute("innerHTML");
            System.out.println(table_html);
        }catch (Exception e){
            List<WebElement> testItems = driver.findElements(By.xpath("//ul[@class='pri_tw market_tw']//li//a"));
            System.out.println(testItems.size());
        }

        return table_html;
    }

}