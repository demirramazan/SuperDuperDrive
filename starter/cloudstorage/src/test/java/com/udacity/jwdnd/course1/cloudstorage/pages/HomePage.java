package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement fileTabButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadElement;

    @FindBy(id = "upload-btn")
    private WebElement uploadButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "noteTable")
    private WebElement notesTable;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTabButton;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "tbl-note-del-btn")
    private WebElement noteDeleteBtn;

    @FindBy(id = "edit-note-btn")
    private WebElement noteEditBtn;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "credential-edit-btn")
    private WebElement editCredentialBtn;

    @FindBy(id = "credential-delete-tbl")
    private WebElement deleteCredentialBtn;

    private WebDriver driver;

    private WebDriverWait webDriverWait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 500);
    }


    public void selectNoteTab() throws InterruptedException {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTabButton)).click();
    }

    public void selectNoteTable() throws InterruptedException {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTable)).click();
    }

    public List<WebElement> notesTableElementList() throws InterruptedException {
        return notesTable.findElements(By.tagName("td"));
    }

    public void clickNoteEditBtn() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
    }

    public void addNewNote(Note note) throws InterruptedException {
        noteTitle.sendKeys(note.getNoteTitle());
        noteDescription.sendKeys(note.getNoteDescription());
    }

    public void clickNoteSaveBtn() {
        noteSubmitButton.submit();
    }

    public Note getNote() {
        String noteTitle = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("tbl-note-title")))).getText();
        String noteDescription = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("tbl-note-desc")))).getText();
        return Note.builder().noteTitle(noteTitle).noteDescription(noteDescription).build();
    }

    public void setNoteTitle(String newNoteTitle) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(newNoteTitle);
    }

    public void setNoteDescription(String newNoteDescription) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(newNoteDescription);
    }

    public void clickEditNote() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteEditBtn)).click();
    }

    public void clickDeleteNote() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteDeleteBtn)).click();
    }

    public void selectCredentialTab() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTabButton)).click();
    }

    public void clickAddCredentialButton() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredentialButton)).click();
    }

    public void addNewCredential(Credential credential) throws InterruptedException {
        credentialUrl.sendKeys(credential.getUrl());
        credentialUsername.sendKeys(credential.getUsername());
        credentialPassword.sendKeys(credential.getPassword());
    }

    public void selectCredentialSaveSubmit() {
        credentialSubmitButton.submit();
    }


    public List<Credential> getCredentials() throws InterruptedException {
        sleep();
        List<WebElement> ids = driver.findElements(By.id("credential-tbl-id"));
        List<WebElement> urls = driver.findElements(By.id("credential-tbl-url"));
        List<WebElement> usernames = driver.findElements(By.id("credential-tbl-uname"));
        List<WebElement> passwords = driver.findElements(By.id("credential-tbl-pass"));
        List<Credential> credentials = new ArrayList<>();
        IntStream.range(0, ids.size())
                .forEach(i -> {
                    String url = urls.get(i).getText();
                    String username = usernames.get(i).getText();
                    String password = passwords.get(i).getText();
                    Credential cred = Credential.builder()
                            .url(url).username(username).password(password)
                            .build();
                    credentials.add(cred);
                });

        return credentials;
    }

    public void selectCredentialTable() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTable)).click();
    }

    public void clickEditCredential() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(editCredentialBtn)).click();
    }


    public void setCredentialUrl(String credentialModifyUrl) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(credentialModifyUrl);
    }

    public void clickDeleteCredential() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteCredentialBtn)).click();
    }

    public void logout() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(500);
    }

}
