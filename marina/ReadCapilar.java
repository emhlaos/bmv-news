package marina;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
public class ReadCapilar {
	public String stockurl;
	public ArrayList<Long> timestamps = new ArrayList<Long>();
	public ArrayList<String> titles = new ArrayList<String>();
	public ArrayList<String> urls = new ArrayList<String>();
	//public ArrayList<String> urls,timestamps,titles = new ArrayList<String>();
	public void getnews() throws ParseException, NumberFormatException, IOException{
		WebDriver driver = new FirefoxDriver();
		boolean cbrada = true;
		long minimum = minimum();
		driver.get(stockurl);
		try {
			driver.findElement(By.cssSelector("body > section.content > div > div.medium > div.box-text > div > div.body-tabs > ul > li:nth-child(1) > h2")).click();
		}
		catch(NoSuchElementException ex) {
			cbrada=false;
			System.out.println(ex);
		}
		if(cbrada) {
			WebElement dtable = driver.findElement(By.id("DataTables_Table_0"));
			int count = 0;
			for(WebElement td:dtable.findElements(By.tagName("td"))) {
				if(count == 0) {
					long stamp = stampit(td.getText());
					//System.out.println(stamp+" "+1522125580);
					if(stamp>minimum) {
					timestamps.add(stamp);
					}
					else {
						cbrada = false;
						break;
					}
				}
				else if(count == 1) titles.add(td.getText());
				else if(count == 2) {
					urls.add(td.findElement(By.linkText("Descargar")).getAttribute("href"));
					count=-1;
				}
				count++;
			}
		if(cbrada) {
			driver.findElement(By.id("DataTables_Table_0_next")).click();
			while(!(((String) driver.findElement(By.id("DataTables_Table_0_next")).getAttribute("class")).contains("disabled"))) {
				dtable = driver.findElement(By.id("DataTables_Table_0"));
				count = 0;
				for(WebElement td:dtable.findElements(By.tagName("td"))) {
					if(count == 0) {
						long stamp = stampit(td.getText());
						if(stamp>1522125580) {
						timestamps.add(stamp);
						}
						else {
							cbrada = false;
							break;
						}
					}
					else if(count == 1) titles.add(td.getText());
					else if(count == 2) {
						urls.add(td.findElement(By.linkText("Descargar")).getAttribute("href"));
						count=-1;
					}
					count++;
				}
				driver.findElement(By.id("DataTables_Table_0_next")).click();
			}
		}
		if(cbrada) {
			dtable = driver.findElement(By.id("DataTables_Table_0"));
			count = 0;
			for(WebElement td:dtable.findElements(By.tagName("td"))) {
				if(count == 0) {
					long stamp = stampit(td.getText());
					if(stamp>1522125580) {
					timestamps.add(stamp);
					}
					else {
						cbrada = false;
						break;
					}
				}
				else if(count == 1) titles.add(td.getText());
				else if(count == 2) {
					urls.add(td.findElement(By.linkText("Descargar")).getAttribute("href"));
					count=-1;
				}
				count++;
			}
		}
		}
		driver.close();
	}
	public long stampit(String dateString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date date = dateFormat.parse(dateString);
		long unixTime = (long)date.getTime()/1000-21600;
		return unixTime;
	}
	public long minimum() throws ParseException, NumberFormatException, IOException {
	    Path path = Paths.get("C:/placebo/lucia/minimum.txt");
	    long unixTime=  Integer.parseInt(Files.readAllLines(path,StandardCharsets.UTF_8).get(0));
		return unixTime;
	}
}
