import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

/**
 * @author Swyns Oscar & De Roover Lobke
 */


public class PersonOverviewPage extends Page {

    @FindBy(id="emailPerson")
    private WebElement email;

    @FindBy(id="firstnamePerson")
    private WebElement firstName; //oscar firstname

    @FindBy(id="lastnamePerson")
    private WebElement lastName; //oscar lastname

    public PersonOverviewPage(WebDriver driver) {
        super(driver);
        this.driver.get(getPath()+"?command=OverviewUsers");//oscar Overview
    }

    public boolean containsUserWithEmail(String email) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.cssSelector("td"));
        boolean found=false;
        for (WebElement listItem:listItems) {
            if (listItem.getText().contains(email)) {
                found=true;
            }
        }
        return found;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getFirstname() {
        return firstName; //oscar firstname
    }

    public WebElement getLastname() {
        return lastName; //oscar lastname
    }
}