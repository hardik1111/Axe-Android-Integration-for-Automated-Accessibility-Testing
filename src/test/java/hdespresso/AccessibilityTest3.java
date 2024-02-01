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
import java.util.Arrays;
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
import com.deque.axe.android.colorcontrast.AxeImage;
import com.deque.axe.android.colorcontrast.ColorContrastResult;
import com.deque.axe.android.colorcontrast.ColorContrastRunner;
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



public class AccessibilityTest3 extends Baseclass2 {
	
	public BufferedImage takeScreenshot(WebElement textView) throws IOException {
	    // Taking a screenshot using Appium
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(scrFile);
		Point point = textView.getLocation();
		int elementWidth = textView.getSize().getWidth();
		int elementHeight = textView.getSize().getHeight();
		BufferedImage image = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);

	    return image;
	}
	    
	    public AxeRect getBoundsFromElement(WebElement element) {
	        Rectangle rect = element.getRect();
	        
	        // The coordinates of the top-left corner
	        int x = rect.getX();
	        int y = rect.getY();
	        // Calculating the coordinates of the bottom-right corner
	        int width = rect.getWidth();
	        int height = rect.getHeight();
	        int right = x + width;
	        int bottom = y + height;

	        return new AxeRect(x, y, right, bottom);
	    }
	
	    
	    private AxeView createAxeViewForTextView(WebElement textView) {
	    	
	    	
	    	AxeRect bounds = getBoundsFromElement(textView);
	        Dimension dimensions = textView.getSize();

	        return new AxeView.Builder() {
	            @Override public AxeRect boundsInScreen() { return bounds; }
	            @Override public String text() { return textView.getText(); }
	            // Set other AxeView properties based on textView properties as needed
				@Override
				public String className() {
					// TODO Auto-generated method stub
					return  textView.getAttribute("className");
				}
				@Override
				public String contentDescription() {
					// TODO Auto-generated method stub
					return textView.getAttribute("content-desc");
				}
				@Override
				public boolean isAccessibilityFocusable() {
					// TODO Auto-generated method stub
					return Boolean.parseBoolean(textView.getAttribute("focusable"));
				}
				@Override
				public boolean isClickable() {
					// TODO Auto-generated method stub
					return Boolean.parseBoolean(textView.getAttribute("clickable"));
				}
				@Override
				public boolean isEnabled() {
					// TODO Auto-generated method stub
					return textView.isEnabled();
				}
				@Override
				public boolean isImportantForAccessibility() {
					// TODO Auto-generated method stub
					return textView.isDisplayed() && textView.isEnabled();
				}
				@Override
				public AxeView labeledBy() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public String packageName() {
					// TODO Auto-generated method stub
					return textView.getAttribute("package");
				}
				@Override
				public String paneTitle() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public String viewIdResourceName() {
					// TODO Auto-generated method stub
					return textView.getAttribute("resource-id");
				}
				@Override
				public String hintText() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public String value() {
					// TODO Auto-generated method stub
					return textView.getText();
				}
				@Override
				public List<AxeView> children() {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				public boolean overridesAccessibilityDelegate() {
					// TODO Auto-generated method stub
					return false;
				}
				@Override
				public boolean isVisibleToUser() {
					// TODO Auto-generated method stub
					return textView.isDisplayed();
				}
				@Override
				public int measuredHeight() {
					// TODO Auto-generated method stub
					return dimensions.getHeight();
				}
				@Override
				public int measuredWidth() {
					// TODO Auto-generated method stub
					return dimensions.getWidth();
				}
				@Override
				public AxeColor textColor() {
					// TODO Auto-generated method stub
					return null;
				}
	        }.build();
	    }

	    private void processAxeResults(AxeResult axeResult, WebElement textView, double contrastRatio) {
	    	 if (axeResult.axeRuleResults.isEmpty()) {
	    	        System.out.println("No accessibility issues found.");
	    	    } else {
	    	        // Iterate over each result
	    	        for (AxeRuleResult ruleResult : axeResult.axeRuleResults) {
	    	            // You might want to filter results based on status or other criteria
	    	            if (!ruleResult.status.equals(AxeStatus.PASS)) {
	    	            	if (ruleResult.ruleId.equals("ColorContrast") && contrastRatio > 4.5)
	    	            	{
	    	            		continue;
	    	            	}
	    	            	else {
	    	                System.out.println("Issue found:");
	    	                System.out.println("Rule ID: " + ruleResult.ruleId);
	    	                System.out.println("Rule Summary: " + ruleResult.ruleSummary);
	    	                System.out.println("Impact: " + ruleResult.impact);
	    	                System.out.println("AxeView ID: " + ruleResult.axeViewId);
	    	                System.out.println("Visible to User: " + ruleResult.isVisibleToUser);

	    	                // Additional details about the properties can be included here
	    	                // You can iterate over ruleResult.props if it contains relevant information

	    	                System.out.println("-------------------------------------------------");
	    	            	}
	    	            }
	    	        }
	    	    }
	    }
	    
	    private void processAxeResultsForNonColorContrast(AxeResult axeResult, WebElement textView) {
	    	 if (axeResult.axeRuleResults.isEmpty()) {
	    	        System.out.println("No accessibility issues found.");
	    	    } else {
	    	        // Iterate over each result
	    	        for (AxeRuleResult ruleResult : axeResult.axeRuleResults) {
	    	            // You might want to filter results based on status or other criteria
	    	            if (!ruleResult.status.equals(AxeStatus.PASS)) {
	    	            	if (ruleResult.ruleId.equals("ColorContrast"))
	    	            	{
	    	            		continue;
	    	            	}
	    	            	else {
	    	                System.out.println("Issue found:");
	    	                System.out.println("Rule ID: " + ruleResult.ruleId);
	    	                System.out.println("Rule Summary: " + ruleResult.ruleSummary);
	    	                System.out.println("Impact: " + ruleResult.impact);
	    	                System.out.println("AxeView ID: " + ruleResult.axeViewId);
	    	                System.out.println("Visible to User: " + ruleResult.isVisibleToUser);

	    	                // Additional details about the properties can be included here
	    	                // You can iterate over ruleResult.props if it contains relevant information

	    	                System.out.println("-------------------------------------------------");
	    	            	}
	    	            }
	    	        }
	    	    }
	    }
	
	
	
	public AxeDevice createAxeDevice(AndroidDriver driver) {
	    // Example values - replace these with actual retrievals from the driver or device
		
	    float dpi = 420 ; // You need to find a way to get the actual DPI
	    String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
	    String osVersion = driver.getCapabilities().getCapability("platformVersion").toString();
	    int screenHeight = driver.manage().window().getSize().getHeight();
	    int screenWidth = driver.manage().window().getSize().getWidth();

	    return new AxeDevice(dpi, deviceName, osVersion, screenHeight, screenWidth);
	}
	
	public static void saveScreenshot(BufferedImage image, String filePath) {
        try {
            // Ensure the path exists or create it
            File outputFile = new File(filePath);
            outputFile.getParentFile().mkdirs();

            // Save the image
            ImageIO.write(image, "png", outputFile);
            System.out.println("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving screenshot: " + e.getMessage());
        }
    }
	
	public void checkColorContrast(ColorContrastResult contrastResult) {
	    if (contrastResult == null || ColorContrastRunner.Confidence.NONE.equals(contrastResult.getConfidence())) {
	        System.out.println("No contrast data available or confidence is none.");
	        return;
	    }

	    AxeColor backgroundColor = contrastResult.getMostLikelyBackgroundColor();
	    AxeColor textColor = contrastResult.getMostLikelyTextColor();
	    String confidence = contrastResult.getConfidence();

	    System.out.println("Background Color: " + (backgroundColor != null ? backgroundColor.toString() : "N/A"));
	    System.out.println("Text Color: " + (textColor != null ? textColor.toString() : "N/A"));
	    System.out.println("Confidence Level: " + confidence);

	    // If you have the contrast ratio, you can also print it out here
	    // For example: System.out.println("Contrast Ratio: " + contrastRatio);
	}
	
	public static double calculateContrastRatio(String color1, String color2) {
        double l1 = calculateLuminance(color1);
        double l2 = calculateLuminance(color2);

        if (l1 > l2) {
            return (l1 + 0.05) / (l2 + 0.05);
        } else {
            return (l2 + 0.05) / (l1 + 0.05);
        }
    }

    private static double calculateLuminance(String color) {
        int r = Integer.parseInt(color.substring(2, 4), 16);
        int g = Integer.parseInt(color.substring(4, 6), 16);
        int b = Integer.parseInt(color.substring(6, 8), 16);

        double rLinear = linearizeColorComponent(r);
        double gLinear = linearizeColorComponent(g);
        double bLinear = linearizeColorComponent(b);

        return 0.2126 * rLinear + 0.7152 * gLinear + 0.0722 * bLinear;
    }

    private static double linearizeColorComponent(int colorComponent) {
        double color = colorComponent / 255.0;
        if (color <= 0.03928) {
            return color / 12.92;
        } else {
            return Math.pow((color + 0.055) / 1.055, 2.4);
        }
    }
	
    @Test
	public void accessibilityTest() throws InterruptedException, IOException
    {
    	List<WebElement> textViews = driver.findElements(By.className("android.widget.TextView"));
    	List<WebElement> buttons = driver.findElements(By.className("android.widget.Button"));
    	List<WebElement> imagebuttons = driver.findElements(By.className("android.widget.ImageButton"));
    	List<WebElement> allImageElements = driver.findElements(By.className("android.widget.ImageView"));
    	List<WebElement> editTextFields = driver.findElements(By.className("android.widget.EditText"));
    	List<WebElement> listViews = driver.findElements(By.className("android.widget.ListView"));
    	List<WebElement> gridViews = driver.findElements(By.className("android.widget.GridView"));
    	List<WebElement> recyclerViews = driver.findElements(By.className("androidx.recyclerview.widget.RecyclerView"));
    	
    	
    	
    	List<List<WebElement>> listOfLists = Arrays.asList(
    		    textViews, buttons, imagebuttons, allImageElements, 
    		    editTextFields, listViews, gridViews, recyclerViews
    		);

    		List<WebElement> combinedList = new ArrayList<>();

    		for (List<WebElement> individualList : listOfLists) {
    		    if (!individualList.isEmpty()) {
    		        combinedList.addAll(individualList);
    		    }
    		}	
    
    	AxeDevice axeDevice =createAxeDevice(driver);
    	Axe axe =new Axe (new AxeConf());
    	for (WebElement element : combinedList) {
    		BufferedImage image = takeScreenshot(element);
    		AxeImage myAxeImage = new MyAxeImage(image);
    		String elementType = element.getAttribute("className");
    		if(elementType.contains("TextView") || elementType.contains("Button") || elementType.contains("ImageButton") || elementType.contains("ImageView") || elementType.contains("EditText") )
    		{
    			AxeRect frame = myAxeImage.frame();
    			ColorContrastResult contrastResult = myAxeImage.runColorContrastCalculation(frame);
    			double contrastRatio = calculateContrastRatio(contrastResult.getMostLikelyBackgroundColor().toString(), contrastResult.getMostLikelyTextColor().toString());
    			AxeView axeView = createAxeViewForTextView(element);
    			AxeContext axeContext = new AxeContext(axeView, axeDevice, myAxeImage, new AxeEventStream()); // Assuming no image analysis
    			try 
    			{
                AxeResult axeResult = axe.run(axeContext);
                if (axeResult != null) {
                    System.out.println("Processing Axe results for element: "+ element.toString()+ " " + element.getText());
                    processAxeResults(axeResult, element,contrastRatio);
                } 
                else 
                {
                    System.out.println("AxeResult is null for element: " + element.toString()+ " "+ element.getText());
                }
    			} 
    			catch (Exception e) 
    			{
                System.out.println("Error during Axe analysis for element: "+ element.toString()+ " " + element.getText() + " : " + e.getMessage());
                e.printStackTrace();
    			}
    		}
    		else
    		{
    			//AxeRect frame = myAxeImage.frame();
    			//ColorContrastResult contrastResult = myAxeImage.runColorContrastCalculation(frame);
    			//double contrastRatio = calculateContrastRatio(contrastResult.getMostLikelyBackgroundColor().toString(), contrastResult.getMostLikelyTextColor().toString());
    			AxeView axeView = createAxeViewForTextView(element);
    			AxeContext axeContext = new AxeContext(axeView, axeDevice, myAxeImage, new AxeEventStream()); // Assuming no image analysis
    			try 
    			{
                AxeResult axeResult = axe.run(axeContext);
                if (axeResult != null) {
                    System.out.println("Processing Axe results for element: "+ element.toString()+ " " + element.getText());
                    processAxeResultsForNonColorContrast(axeResult, element);
                } 
                else 
                {
                    System.out.println("AxeResult is null for element: " + element.toString()+ " "+ element.getText());
                }
    			} 
    			catch (Exception e) 
    			{
                System.out.println("Error during Axe analysis for element: "+ element.toString()+ " " + element.getText() + " : " + e.getMessage());
                e.printStackTrace();
    			}
    		}
    	
        }
    	
    	
    	
    	//AxeImage axeImage =myAxeImage.createAxeImage(image,myAxeImage);
		//AxeRect frame = myAxeImage.frame();// The area to analyze
		//AxeColor actualTextColor = new AxeColor("E1E2E2"); // The color of the text
		//ColorContrastResult contrastResult = myAxeImage.runColorContrastCalculation(frame);
		//checkColorContrast(contrastResult);
		//System.out.println(contrastResult.getMostLikelyBackgroundColor().toString()+" "+contrastResult.getMostLikelyTextColor().toString());
		//double contrastRatio = calculateContrastRatio(contrastResult.getMostLikelyBackgroundColor().toString(), contrastResult.getMostLikelyTextColor().toString());
        //System.out.println("Contrast Ratio: " + contrastRatio);
		
        
    	/*List<WebElement> textViews = driver.findElements(By.className("android.widget.TextView"));
    	
    	AxeDevice axeDevice =createAxeDevice(driver);
         // Now create an instance of your MyAxeImage subclass using the screenshot
         
    	Axe axe =new Axe (new AxeConf());
    	
    	for (WebElement textView : textViews) {
    		
    		
    		System.out.println("Element: "+ textView.getText());
    		
    		BufferedImage image = takeScreenshot(textView);
    		MyAxeImage myAxeImage = new MyAxeImage(image);
    		
            AxeView axeView = createAxeViewForTextView(textView);
            AxeContext axeContext = new AxeContext(axeView, axeDevice, myAxeImage, new AxeEventStream());

           
            AxeResult axeResult = axe.run(axeContext);

            // Process the results for each TextView
            processAxeResults(axeResult,textView);
            System.out.println();
        }*/	
    	
    }

}
    																									


    