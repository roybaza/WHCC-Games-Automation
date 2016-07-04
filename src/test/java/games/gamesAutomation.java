package games;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by royba on 14/06/2016
 */
public class gamesAutomation {
    private WebDriver driver;
    private parsing pars = new parsing();
    private String [] NgmGameErrors = new String[pars.NgmgamesUrlSize()];
    private String [] MgpGameErrors = new String[pars.NgmgamesUrlSize()];

    @Before
    public void beforeTest () {

    }

    @After
    public void afterTest () throws IOException {
        driver.quit();

        // Kill chrome
        String killChrome[] = {"killall","Google Chrome"};
        Runtime.getRuntime().exec(killChrome);

        // Kill terminal
        String killTerminal[] = {"killall","Terminal"};
        Runtime.getRuntime().exec(killTerminal);
    }

    @Test
    public void loginToWebsite() {
        // login
        System.setProperty("webdriver.chrome.driver", "/Users/royba/IdeaProjects/Games-Automation/src/test/java/games/driver/chromedriver");

        //-----------------
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", "Google Nexus 5");

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("mobileEmulation", mobileEmulation);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        driver = new ChromeDriver(capabilities);
        //------------

        //WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://mobile.williamhillcasino.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebElement LoginCTA = driver.findElement(By.className("btn_action_login"));
        LoginCTA.click();
        WebElement userName = driver.findElement(By.name("userName"));
        userName.sendKeys("whosagametest");
        WebElement passWord = driver.findElement(By.name("password"));
        passWord.sendKeys("Br123456");
        WebElement Login = driver.findElement(By.className("login_btn"));
        Login.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        testGames();
        sendEmail();
    }

    private String getResultsFromNgmParaser(int index) {
        pars = new parsing();
        String [] NgmGameUrl = pars.NgmgameParser();
        return NgmGameUrl[index];
    }

    private String getResultsFromMgpParaser(int index) {
        pars = new parsing();
        String [] MgpGameUrl = pars.MgpgameParser();
        return MgpGameUrl[index];
    }

    private boolean isElementExists () {

        boolean isExist = true;
        try {
            driver.switchTo().frame(driver.findElement(By.className("game-iframe")));
            driver.findElement(By.id("messageBoxTextContainer"));
        } catch (NoSuchElementException e) {
                isExist = false;
            }

        return isExist;
    }

    private void NgmTestGames(){
        pars = new parsing();

        for(int i = 0 ; i < pars.NgmgamesUrlSize(); i++) {
            if(getResultsFromNgmParaser(i).equals("Not In List"))
                continue;
            else{
                //System.out.println(getResultsFromNgmParaser(i));
                driver.navigate().to(getResultsFromNgmParaser(i));
            }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            boolean isElementExists = isElementExists();
            if(isElementExists){
                NgmGameErrors[i] = ("Error Message been return for NGM game: " + getResultsFromNgmParaser(i));
//                System.out.println("Error Message been return for NGM game: " + getResultsFromNgmParaser(i));
            }
            else{
                NgmGameErrors[i] = null;
            }


        }
    }

    private void loginToMgpGame(){
        Actions actions = new Actions(driver);
        //driver.switchTo().frame(driver.findElement(By.id("Lobby")));
        WebElement userName = driver.findElement(By.cssSelector("input[type='username']"));
        userName.sendKeys("whosagametest");
        WebElement passWord = driver.findElement(By.cssSelector("input[type='password']"));
        passWord.sendKeys("Br123456");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement Login = driver.findElement(By.className("gwt-Button"));
        actions.moveToElement(Login).build().perform();
    }

    private void MgpTestGames(){
        pars = new parsing();

        for(int i = 0 ; i < pars.MgpgamesUrlSize(); i++) {
            if(getResultsFromMgpParaser(i).equals("Not In List"))
                continue;
            else{
                //System.out.println(getResultsFromMgpParaser(i));
                driver.navigate().to(getResultsFromMgpParaser(i));
            }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            loginToMgpGame();

            boolean isElementExists = isElementExists();
            if(isElementExists){
                MgpGameErrors[i] = ("Error Message been return for MGP game: " + getResultsFromMgpParaser(i));
//                System.out.println("Error Message been return for MGP game: " + getResultsFromMgpParaser(i));
            }else{
                MgpGameErrors[i] = null;
            }

        }
    }

    private void testGames() {
        NgmTestGames();
        MgpTestGames();

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
        Content.sendKeys("Please see mobile games test result: \n");
        System.out.println();
        int count = 0;
        for (String NgmGameError : NgmGameErrors) {
            if (NgmGameError != null) {
                Content.sendKeys(NgmGameError + "\n");
                count++;
            }

        }

        for (String MgpGameError : MgpGameErrors) {
            if (MgpGameError != null) {
                Content.sendKeys(MgpGameError + "\n");
                count++;
            }
        }
        if(count == 0){
            Content.sendKeys(pars.NgmgamesUrlSize() + "Games tests passed successfully \n");
        }

        WebElement Send = driver.findElement(By.xpath("//div[text()='Send']"));
        Send.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        System.out.println("Send Email.");
    }

}