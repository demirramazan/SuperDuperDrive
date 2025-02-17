package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void setPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    public void submit(){
        usernameField.submit();
    }
}
