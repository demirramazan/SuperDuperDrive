package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    public static final String NOTE_MODIFY_TITLE = "Note Modify Title";
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private SignUpPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    private final static String FIRST_NAME = "Ramazan";
    private final static String LAST_NAME = "Demir";
    private final static String USER_NAME = "rdemir";
    private final static String PASSWORD = "1234";

    private final static String CREDENTIAL_URL = "https://www.google.com";
    private final static String CREDENTIAL_USERNAME = "ramazan";
    private final static String CREDENTIAL_PASSWORD = "1234";

    private final static String CREDENTIAL_MODIFY_URL = "https://www.facebook.com";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(driver, 500);
        homePage = new HomePage(this.driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(1)
    protected void signUpAndLogin() throws InterruptedException {
        signUp();
        login();
    }

    protected void signUp() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage = new SignUpPage(driver);
        signupPage.setFirstNameField(FIRST_NAME);
        signupPage.setLastNameField(LAST_NAME);
        signupPage.setUserName(USER_NAME);
        signupPage.setPasswordField(PASSWORD);
        signupPage.submit();
        homePage.sleep();
    }

    public void login() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(USER_NAME);
        loginPage.setPasswordField(PASSWORD);
        loginPage.submit();
        homePage.sleep();
    }

    @Test
    @Order(3)
    public void addNoteTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectNoteTab();
        Note note = Note.builder().noteTitle("Note Sample")
                .noteDescription("This is my sample note description").build();
        homePage.clickNoteEditBtn();
        homePage.sleep();
        homePage.addNewNote(note);
        homePage.clickNoteSaveBtn();
        homePage.sleep();
        homePage.selectNoteTab();
        homePage.selectNoteTable();
        Note n = homePage.getNote();
        homePage.sleep();
        assertEquals(n.getNoteTitle(), note.getNoteTitle());
        assertEquals(n.getNoteDescription(), note.getNoteDescription());
    }

    @Test
    @Order(4)
    public void editNoteTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectNoteTab();
        Note note = Note.builder().noteTitle("Note Sample")
                .noteDescription("This is my sample note description")
                .build();
        homePage.clickNoteEditBtn();
        homePage.sleep();
        homePage.addNewNote(note);
        homePage.clickNoteSaveBtn();
        homePage.sleep();
        homePage.selectNoteTab();
        homePage.selectNoteTable();
        homePage.clickEditNote();
        homePage.setNoteTitle(NOTE_MODIFY_TITLE);
        homePage.clickNoteSaveBtn();
        homePage.selectNoteTab();
        Note n = homePage.getNote();
        homePage.sleep();
        assertEquals(n.getNoteTitle(), NOTE_MODIFY_TITLE);
        assertNotNull(n);
    }

    @Test
    @Order(5)
    public void deleteNoteTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectNoteTab();
        Note note = Note.builder().noteTitle("Note Sample")
                .noteDescription("This is my sample note description")
                .build();
        homePage.clickNoteEditBtn();
        homePage.sleep();
        homePage.addNewNote(note);
        homePage.clickNoteSaveBtn();
        homePage.sleep();
        homePage.selectNoteTab();
        homePage.selectNoteTable();
        homePage.clickDeleteNote();
        homePage.selectNoteTab();
        assertEquals("Home", driver.getTitle());
    }

    @Test
    @Order(6)
    public void addCredentialTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectCredentialTab();
        Credential credential = Credential.builder()
                .url(CREDENTIAL_URL)
                .username(CREDENTIAL_USERNAME)
                .password(CREDENTIAL_PASSWORD)
                .build();
        homePage.clickAddCredentialButton();
        homePage.sleep();
        homePage.addNewCredential(credential);
        homePage.selectCredentialSaveSubmit();
        homePage.sleep();
        homePage.selectCredentialTab();
        List<Credential> credentials = homePage.getCredentials();
        homePage.sleep();
        assertTrue(credentials.size() > 0);
        assertEquals(credentials.get(0).getUrl(), CREDENTIAL_URL);
    }

    @Test
    @Order(7)
    public void updateCredentialTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectCredentialTab();
        Credential credential = Credential.builder()
                .url(CREDENTIAL_URL)
                .username(CREDENTIAL_USERNAME)
                .password(CREDENTIAL_PASSWORD)
                .build();
        homePage.clickAddCredentialButton();
        homePage.sleep();
        homePage.addNewCredential(credential);
        homePage.selectCredentialSaveSubmit();
        homePage.sleep();
        homePage.selectCredentialTab();;
        homePage.selectCredentialTable();
        homePage.clickEditCredential();
        homePage.setCredentialUrl(CREDENTIAL_MODIFY_URL);
        homePage.selectCredentialSaveSubmit();
        homePage.selectCredentialTab();
        homePage.sleep();
        List<Credential> credentials = homePage.getCredentials();
        homePage.sleep();
        assertTrue(credentials.size() > 0);
        assertEquals(credentials.get(0).getUrl(), CREDENTIAL_MODIFY_URL);
        assertEquals("Home", driver.getTitle());
    }

    @Test
    @Order(8)
    public void deleteCredentialTest() throws InterruptedException {
        signUpAndLogin();
        driver.get("http://localhost:" + this.port + "/home");
        homePage = new HomePage(driver);
        homePage.selectCredentialTab();
        homePage.sleep();
        Credential credential = Credential.builder()
                .url(CREDENTIAL_URL)
                .username(CREDENTIAL_USERNAME)
                .password(CREDENTIAL_PASSWORD)
                .build();
        homePage.clickAddCredentialButton();
        homePage.sleep();
        homePage.addNewCredential(credential);
        homePage.sleep();
        homePage.selectCredentialSaveSubmit();
        homePage.sleep();
        homePage.selectCredentialTab();;
        homePage.sleep();
        homePage.selectCredentialTable();;
        homePage.clickDeleteCredential();
        homePage.sleep();
        homePage.selectCredentialTab();
        homePage.sleep();
        assertEquals("Home", driver.getTitle());
    }


}





