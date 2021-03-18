package commons;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.grid.internal.exception.NewSessionException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AbstractTest {

	private WebDriver driver;

	protected WebDriver getbrowserDriver(String url, String browserName) {
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {

			driver = new SafariDriver();
		} else {
			throw new RuntimeException("Please input valid browser name value ! ");
		}
		// driver.manage().timeouts().implicitlyWait(GlobalConstans.LONG_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}

	protected WebDriver getbrowserDriver(String url, String browserName, String ipAddres, String portNumber) {
		DesiredCapabilities capability = null;
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(Platform.WINDOWS);

			FirefoxOptions options = new FirefoxOptions();
			options.merge(capability);
		} else if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.WINDOWS);

			ChromeOptions options = new ChromeOptions();
			options.merge(capability);
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internetexplorer");
			capability.setPlatform(Platform.WINDOWS);

			capability.setJavascriptEnabled(true);
		} else if (browserName.equalsIgnoreCase("safari")) {
			capability = DesiredCapabilities.safari();
			capability.setBrowserName("safari");
			capability.setPlatform(Platform.MAC);

			capability.setJavascriptEnabled(true);
		} else {
			throw new RuntimeException("Please input valid browser name value ! ");
		}
		try {
			driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddres, portNumber)), capability);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}

	protected WebDriver getbrowserDriverOnBrowserStack(String url, String osName, String osVersion, String browserName, String browserVersion) {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("os", osName);
		capability.setCapability("os_version", osVersion);
		capability.setCapability("browser", browserName);
		capability.setCapability("browser_version", browserVersion);
		capability.setCapability("browserstack.debug", "true");
		capability.setCapability("browserstack.selenium_version", "3.141.59");
		capability.setCapability("name", "Run on " + osName + " and " + browserName + " with version " + browserVersion);

		try {
			driver = new RemoteWebDriver(new URL(GlobalConstants.BROWSER_STACK_URL), capability);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}

	protected WebDriver getbrowserDriverOnSauceLabs(String url, String osName, String browserName, String browserVersion) {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("os", osName);
		capability.setCapability("browser", browserName);
		capability.setCapability("browser_version", browserVersion);
		capability.setCapability("name", "Run on " + osName + " and " + browserName + " with version " + browserVersion);
		try {
			driver = new RemoteWebDriver(new URL(GlobalConstants.SAUCE_STACK_URL), capability);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}
}
