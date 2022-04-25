package de.zooplus.framework.config;

import de.zooplus.framework.constants.DriverConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverProvider {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isLinux() {
        return (OS.indexOf("nix") >= 0);
    }

    public static WebDriver getDriver(String browser, boolean isHeadless) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            if (isHeadless) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1440,900");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--allow-insecure-localhost");
                chromeOptions.addArguments("--no-sandbox");
            } else {
                chromeOptions.addArguments("--start-maximized");
            }
            if (isMac()) {
                System.setProperty("webdriver.chrome.driver", DriverConstants.CHROME_DRIVER_PATH_MAC);
            }
            if (isWindows()) {
                System.setProperty("webdriver.chrome.driver", DriverConstants.CHROME_DRIVER_PATH_WINDOWS);
            }
            if (isLinux()) {
                System.setProperty("webdriver.chrome.driver", DriverConstants.CHROME_DRIVER_PATH_LINUX);
            }
            return new ChromeDriver(chromeOptions);
        }

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (isHeadless) {
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--window-size=1440,900");
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.addArguments("--allow-insecure-localhost");
                firefoxOptions.addArguments("--no-sandbox");
            } else {
                firefoxOptions.addArguments("--start-maximized");
            }
            if (isMac()) {
                System.setProperty("webdriver.gecko.driver", DriverConstants.FIREFOX_DRIVER_PATH_MAC);
            }
            if (isWindows()) {
                System.setProperty("webdriver.gecko.driver", DriverConstants.FIREFOX_DRIVER_PATH_WINDOWS);
            }
            if (isLinux()) {
                System.setProperty("webdriver.gecko.driver", DriverConstants.FIREFOX_DRIVER_PATH_LINUX);
            }
            return new FirefoxDriver(firefoxOptions);
        }

        return null;
    }
}
