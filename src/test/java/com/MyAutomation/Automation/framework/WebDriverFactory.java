package com.MyAutomation.Automation.framework;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    public static WebDriver createWebDriver() {

        WebDriver driver = null;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--whitelisted-ips");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--start-maximized");
        options.setCapability("download.prompt_for_download", false);
        options.setCapability("plugins.always_open_pdf_externally", true);
        options.setCapability("download.directory_upgrade", true);
        options.setCapability("safebrowsing.enabled", true);

            System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browser\\chromedriver.exe");
            driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebDriverRunner.setWebDriver(driver);
        return driver;
    }
}
