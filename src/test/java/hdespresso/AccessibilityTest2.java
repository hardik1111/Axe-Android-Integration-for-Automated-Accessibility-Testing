package hdespresso;

import static org.testng.Assert.assertNotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deque.axe.android.Axe;
import com.deque.axe.android.AxeResult;
import com.deque.axe.android.AxeRuleResult;
import com.deque.axe.android.constants.AxeStatus;
import com.deque.axe.android.utils.*;
import com.deque.axe.android.wrappers.AxeEventStream;
import com.deque.axe.android.wrappers.AxeRect;
import com.deque.axe.android.AxeView;
import com.deque.axe.android.MyAxeImage;
import com.deque.axe.android.colorcontrast.AxeColor;
import com.google.common.collect.ImmutableMap;
import com.deque.axe.android.AxeContext;
import com.deque.axe.android.AxeDevice;
import com.deque.axe.android.AxeConf;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;



public class AccessibilityTest2 extends Baseclass {
	
	
    @Test
	public void accessibilityTest() throws InterruptedException, IOException
    {
    	WebElement elem = driver.findElement(AppiumBy.accessibilityId("Access'ibility"));

    	
    	
    }	
    	

}
    																									


    