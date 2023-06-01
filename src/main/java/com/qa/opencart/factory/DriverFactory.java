package com.qa.opencart.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogExporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

	public static String highlight;
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * this method is use to intialize a webdriver
	 *
	 * @parambrowserName
	 * @return will return the driver
	 */

	public WebDriver init_Driver(Properties prop) {

		String browserName =prop.getProperty("browser");
		System.out.println("browser name is " + browserName);

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			/* ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");*/
			//driver = new ChromeDriver(optionsManager.getChromeOptions());

			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			//tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

		} else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		} else {
			System.out.println(" Please pass the right browser" + browserName);
		}
		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}


	/**
	 * getdriver(): It will return a thread local copy of the webdriver
	 */
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is use to initialize the Properties
	 * @return will return properties prop reference
	 */


	/*
    ./ means from  current project directory you traverse
       new FileInputStream("./src/test/resources/config/config.properties");
	 */
	public Properties init_prop() {
		prop = new Properties();

		FileInputStream fis = null;


		String envName = System.getProperty("env"); //qa/dev/stage/uat


		if(envName == null)
		{
			System.out.println("running on prod envName");
			try 
			{

				fis = new FileInputStream("./src/test/resources/config/config.properties");

			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}

		}	
		else
		{
			System.out.println("running on envName"+ envName);
			try {
				switch(envName.toLowerCase())
				{

				case "qa":
					fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "stage":
					fis = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;

				default:
					System.out.println("Please pass the right environment");
					break;

				}
			}

			catch(FileNotFoundException e )
			{
				e.printStackTrace();
			}


		}

		try
		{
			prop.load(fis);
		}
		catch(IOException e)
		{

			e.printStackTrace();
		}

		return prop;

	}

	/**
	 * the below method takes the screenshot
	 * 
	 * user.dir means current project directory in our case it will April042023POMSeries
	 */

	public static String getScreenshot()
	{
		File srcFile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshots/"+System.currentTimeMillis()+".png";

		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}





















}
