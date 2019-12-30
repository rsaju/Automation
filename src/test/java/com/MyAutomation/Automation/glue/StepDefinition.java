package com.MyAutomation.Automation.glue;

import com.MyAutomation.Automation.framework.WebDriverFactory;
import com.codeborne.selenide.SelenideElement;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Properties;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;


public class StepDefinition {

    private Selector selector;
    private final WebDriver driver = WebDriverFactory.createWebDriver();
    private Properties OR = new Properties();

    public enum Selector {
        XPATH,
        CSS,
        ID,
        CLASS,
        TEXT;
    }

    @Before
    public void setUp() throws Exception {
        clearBrowserCache();
        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//config//OR.properties");
            OR.load(ip);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private SelenideElement bySelector(String input) {
        if (input.startsWith("#")) {
            return $(input);
        }
        if (selector != null) {
            switch (selector) {
                case XPATH:
                    return $(By.xpath(OR.getProperty(input)));
                case CSS:
                    return $(By.cssSelector(input));
                case ID:
                    return $(By.id(input));
                case CLASS:
                    return $(By.className(input));
                case TEXT:
                    return $(byText(input));

                default:
                    return $(By.xpath(OR.getProperty(input)));
            }
        } else {
            return $(By.xpath(OR.getProperty(input)));
        }
    }

    @Given("^I navigate to (.*)$")
    public void iNavigateTo(String url) {
        // Write code here that turns the phrase above into concrete actions
        open(url);
    }

    @When("^I click (.*)$")
    public void iClickSignin_link(String signin) {
        // Write code here that turns the phrase above into concrete actions
//        $(By.xpath(OR.getProperty(signin))).click();
        bySelector(signin).click();

    }

    @After()
    public void closeBrowser(Scenario scenario) {
        if(scenario.isFailed()){
            scenario.write("Current Page URL is " + driver.getCurrentUrl());
            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
        driver.quit();
    }


    @And("^I wait for (.*) minutes$")
    public void iWaitForMinutes(int minuts) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Thread.sleep(minuts * 1000 * 60);
    }
}
