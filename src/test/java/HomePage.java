import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Swyns Oscar & De Roover Lobke
 */

public class HomePage extends Page {

    @FindBy(id = "aanmelden")//oscar login
    private WebElement loginButton;

    @FindBy(id = "useridlogin")//oscar userid
    private WebElement userID;

    @FindBy(id = "passwordlogin")//oscar password
    private WebElement password;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver.get(getPath() + "?command=Index");
        //oscar Home
    }

    public void setUserID(String userID) {
        this.userID.clear();
        this.userID.sendKeys(userID);
    }

    public void setPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public HomePage submitValid() {
        loginButton.click();
        return PageFactory.initElements(driver,HomePage.class);
    }

}