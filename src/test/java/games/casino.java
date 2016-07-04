package games;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
/**
 * Created by royba on 14/06/2016.
 */
public class casino {

    private WebDriver driver;


    @Before
    public void beforeTest () {

    }

    @After
    public void afterTest () throws IOException {
        driver.quit();

        // Kill chrome
        String killChrome[] = {"killall","Google Chrome"};
        Runtime.getRuntime().exec(killChrome);

    }

    @Test
    public void loginToWebsite() {
        // login
        System.setProperty("webdriver.chrome.driver", "/Users/royba/IdeaProjects/Games-Automation/src/test/java/games/driver/chromedriver");

        //-----------------

        driver = new ChromeDriver();
        //------------

        //WebDriver driver = new ChromeDriver();
        for(int i = 6; i < 100; i++) {
            driver.navigate().to("http://static.williamhillcasino.com/casino-uk-test/");
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            WebElement JoinCTA = driver.findElement(By.id("playButton"));
            JoinCTA.click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
            WebElement firstName = driver.findElement(By.name("firstName"));
            firstName.sendKeys("marketingtest");
            WebElement lastName = driver.findElement(By.name("lastName"));
            lastName.sendKeys("marketingtest");
            Select birthDay = new Select(driver.findElement(By.id("birthDay")));
            birthDay.selectByVisibleText("01");
            Select birthMonth = new Select(driver.findElement(By.id("birthMonth")));
            birthMonth.selectByVisibleText("January");
            Select birthYear = new Select(driver.findElement(By.id("birthYear")));
            birthYear.selectByVisibleText("1998");
            Select title = new Select(driver.findElement(By.id("title")));
            title.selectByVisibleText("Mr");
            WebElement email = driver.findElement(By.name("email"));
            email.sendKeys("test@playtech.com");
            WebElement city = driver.findElement(By.name("city"));
            city.sendKeys("marketingtest");
            WebElement address = driver.findElement(By.name("address"));
            address.sendKeys("marketingtest");
            WebElement postCode = driver.findElement(By.name("postCode"));
            postCode.sendKeys("1234");
            WebElement phone = driver.findElement(By.name("phone"));
            phone.sendKeys("12345678");
            WebElement userName = driver.findElement(By.name("userName"));
            userName.sendKeys("marketingtest"+i);
            WebElement userPassword = driver.findElement(By.name("userPassword"));
            userPassword.sendKeys("Br123456");
            driver.findElement(By.id("termsAndConditions")).click();
            WebElement createAccountCTA = driver.findElement(By.className("large"));
            createAccountCTA.click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        }
        sendEmail();

    }

    private void sendEmail(){
        driver = new ChromeDriver();
        driver.navigate().to("https://www.gmail.com/");
        WebElement userName = driver.findElement(By.id("Email"));
        userName.sendKeys("whosagametest@gmail.com");
        WebElement Next = driver.findElement(By.id("next"));
        Next.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement passWord = driver.findElement(By.id("Passwd"));
        passWord.sendKeys("gametest2016");
        WebElement Login = driver.findElement(By.id("signIn"));
        Login.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement Compose = driver.findElement(By.xpath("//div[text()='COMPOSE']"));
        Compose.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement To = driver.findElement(By.name("to"));
        To.sendKeys("roy.baza@williamhill.com");
        WebElement Subject = driver.findElement(By.name("subjectbox"));
        Subject.sendKeys("game test automation email");
        WebElement Content = driver.findElement(By.xpath("//div[@role='textbox']"));
        Content.sendKeys("Please see Marketing test result: \n");
        System.out.println();
        System.out.println("All users has been created marketingtest0  To  marketingtest100");

        WebElement Send = driver.findElement(By.xpath("//div[text()='Send']"));
        Send.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        System.out.println("Send Email.");
    }
}
