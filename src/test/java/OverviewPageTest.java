import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

/**
 * @author Swyns Oscar & De Roover Lobke
 */


public class OverviewPageTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
    }

    /*admin ziet page*/
    @Test
    public void test_PersonOverview_LoggedInAsAdmin_ShowsOverviewPage() {
        HomePage homePage = signInAsAdmin();
        assertEquals("Welkom", homePage.getTitle()); //oscar home

        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);

        assertEquals("Gebruikers", (personOverviewPage.getTitle()));//Oscar overview

    }

    @Test
    public void test_PersonOverview_RegistersNewUser_AdminSeesNewUserOnOverviewPage() {
        HomePage homePage;
        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        String randomString = genRandomUserID("monke");

        signUpPage.setUserid(randomString);
        signUpPage.setFirstName("Oscar");
        signUpPage.setLastName("Swyns");
        signUpPage.setEmail(randomString + "@osc.ar");
        signUpPage.setPassword("ww");

        homePage = signUpPage.submitValid();

        homePage = signInAsAdmin();

        assertEquals("Welkom", homePage.getTitle()); //oscar Home

        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);


        assertTrue(personOverviewPage.containsUserWithEmail(randomString + "@osc.ar"));
    }

    @Test
    public void test_PersonOverview_RegisterNormalUser_NormalUserIsNotAllowedToSeeOverviewPage() {
        HomePage homePage;
        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        String randomString = genRandomUserID("monke");

        signUpPage.setUserid(randomString);
        signUpPage.setFirstName("Oscar");
        signUpPage.setLastName("Swyns");
        signUpPage.setEmail(randomString + "@osc.ar");
        signUpPage.setPassword("ww");
        homePage = signUpPage.submitValid();

        homePage = loginAsUser(randomString, "ww", homePage);

        assertEquals("Welkom", homePage.getTitle()); //oscar home

        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.error-danger ul li")); //oscar div.alert-danger
        //assert both lobke and my error for an incorrect overviewPage access
        assertTrue(
                errorMsg.getText().equals("only admins can see all users")
                        || driver.getTitle().equals("Error"));
    }

    @Test
    public void test_PersonOverview_NoOneLoggedIn_NotAllowedToSeeOverview() {
        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.error-danger ul li")); //oscar div.alert-danger
        //assert both lobke and my error for an incorrect overviewPage access
        assertTrue(
                errorMsg.getText().equals("log in as admin to see this page")
                        | driver.getTitle().equals("Error"));

    }

    @Test
    public void test_PersonOverview_FailingOfRegistrationOfNewUser_NewUserNotShownInOverviewPage() {
        HomePage homePage;
        SignUpPage signUpPage = PageFactory.initElements(driver, SignUpPage.class);
        String randomString = genRandomUserID("monke");

        signUpPage.setUserid(randomString);
        signUpPage.setFirstName("Oscar");
        signUpPage.setLastName("Swyns");
        String wrongEmail = randomString + "oscar";
        signUpPage.setEmail(wrongEmail);
        signUpPage.setPassword("ww");

        signUpPage.submitInvalid();

        assertTrue(
                signUpPage.hasErrorMessage("Email not valid")
                        || signUpPage.hasErrorMessage("E-mail is niet geldig."));

        homePage = signInAsAdmin();

        assertEquals("Welkom", homePage.getTitle()); //oscar Home

        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);


        assertFalse(personOverviewPage.containsUserWithEmail(wrongEmail));
    }

    @Test
    public void test_PersonOverview_AdminGoesToOverview_OverviewTableShowsEmailFirstnameAndLastname(){
        HomePage homePage = signInAsAdmin();
        assertEquals("Welkom", homePage.getTitle()); //oscar Home

        PersonOverviewPage personOverviewPage = PageFactory.initElements(driver, PersonOverviewPage.class);
        //if we get here the overviewpage object has successfully found the relevant column titles(email,firstname,lastname)
        assertTrue(true);
    };

    @After
    public void clean() {
        driver.quit();
    }


    private HomePage loginAsUser(String userid, String password, HomePage homePage) {
        homePage.setUserID(userid);
        homePage.setPassword(password);
        return homePage.submitValid();
    }

    private HomePage signInAsAdmin() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserID("admin");
        homePage.setPassword("admin");
        return homePage.submitValid();
    }

    private String genRandomUserID(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

}