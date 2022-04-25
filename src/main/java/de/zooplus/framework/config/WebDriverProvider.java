package de.zooplus.framework.config;

import de.zooplus.framework.constants.DriverConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverProvider {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static WebDriver getDriver(String browser, boolean isHeadless) {
        if(browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            if(isHeadless) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1440,900");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--allow-insecure-localhost");
                chromeOptions.addArguments("--no-sandbox");
            } else {
                chromeOptions.addArguments("--start-maximized");
            }
            if(isMac()) {
                System.setProperty("webdriver.chrome.driver", DriverConstants.CHROME_DRIVER_PATH_MAC);
            }
            if(isWindows()) {
                System.setProperty("webdriver.chrome.driver", DriverConstants.CHROME_DRIVER_PATH_WINDOWS);
            }
            return new ChromeDriver(chromeOptions);
        }
        return null;
    }
}
