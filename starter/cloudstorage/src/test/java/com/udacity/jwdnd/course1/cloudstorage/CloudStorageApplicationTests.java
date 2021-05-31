package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private SignUpPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    private final static String FIRST_NAME = "Ramazan";
    private final static String LAST_NAME = "Demir";
    private final static String USER_NAME = "rdemir";
    private final static String PASSWORD = "1234";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    protected void signUpAndLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.setFirstNameField(FIRST_NAME);
        signupPage.setLastNameField(LAST_NAME);
        signupPage.setUserName(USER_NAME);
        signupPage.setPasswordField(PASSWORD);
        signupPage.submit();
        Thread.sleep(1000);
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(USER_NAME);
        loginPage.setPasswordField(PASSWORD);
        loginPage.submit();
        Thread.sleep(500);
    }

    public void login() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(USER_NAME);
        loginPage.setPasswordField(PASSWORD);
        loginPage.submit();
        Thread.sleep(500);
    }

}





