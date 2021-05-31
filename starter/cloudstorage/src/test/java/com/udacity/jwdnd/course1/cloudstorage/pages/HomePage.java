package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HomePage {
    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement fileTabButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDesc;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredButton;

    @FindBy(id = "credential-url")
    private WebElement credUrl;

    @FindBy(id = "credential-username")
    private WebElement credUsername;

    @FindBy(id = "credential-password")
    private WebElement credPassword;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credsTabButton;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        logoutButton.submit();
    }

    public void selectNotesWindow() throws InterruptedException {
        Thread.sleep(500);
        notesTabButton.click();
    }

    public void selectCredentialsWindow() throws InterruptedException {
        sleep();
        credsTabButton.click();
    }

    public String getMessage() {
        try {
            WebElement e = driver.findElement(By.id("message"));
            String msg = e.getText();
            return msg;
        } catch (Exception e) {
            return null;
        }
    }

    public void addNote(String title, String desc) throws InterruptedException {
        sleep();
        addNoteButton.click();
        sleep();
        noteTitle.sendKeys(title);
        noteDesc.sendKeys(desc);
        noteTitle.submit();
    }

    public List<Note> getNotes() throws InterruptedException {
        sleep();

        List<WebElement> ids = driver.findElements(By.className("noteId"));
        List<WebElement> titles = driver.findElements(By.className("noteTitle"));
        List<WebElement> descriptions = driver.findElements(By.className("noteDescription"));
        List<Note> noteList = new ArrayList<>();

        IntStream.range(0, ids.size()).forEach(i -> {
            Integer id = Integer.parseInt(ids.get(i).getAttribute("value"));
            String title = titles.get(i).getText();
            String desc = descriptions.get(i).getText();
            Note note = Note.builder().noteTitle(title).noteDescription(desc).build();
            note.setNoteId(id);
            noteList.add(note);
        });

        return noteList;
    }

    public boolean noteIfPresent(Note n, List<Note> notes) {
        return notes.stream().anyMatch(note -> note.getNoteTitle().equals(n.getNoteTitle())
                && note.getNoteDescription().equals(n.getNoteDescription()));
    }

    public void updateNote(int noteid, String title, String desc) throws InterruptedException {
        WebElement editNoteButton = driver.findElement(By.id("" + noteid));
        editNoteButton.click();
        sleep();
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDesc.clear();
        noteDesc.sendKeys(desc);
        noteTitle.submit();
    }

    public Integer getNoteId(String notetitle, String notedescription, ArrayList<Note> notes) {
        Integer noteId = notes.stream().filter(n -> n.getNoteTitle().equals(notetitle)
                && n.getNoteDescription().equals(notedescription))
                .map(Note::getNoteId).findFirst().orElse(null);

        return noteId;
    }

    public void deleteNote(Integer id) throws InterruptedException {
        WebElement delNoteButton = driver.findElement(By.id("delete" + id));
        delNoteButton.click();
        sleep();
    }


    public void addCred(String url, String username, String password) throws InterruptedException {
        addCredButton.click();
        sleep();
        credUrl.sendKeys(url);
        credUsername.sendKeys(username);
        credPassword.sendKeys(password);
        credUrl.submit();
    }

    public List<Credential> getCredentials() throws InterruptedException {
        sleep();
        List<WebElement> ids = driver.findElements(By.className("credentialId"));
        List<WebElement> urls = driver.findElements(By.className("credentialUrl"));
        List<WebElement> usernames = driver.findElements(By.className("credentialUsername"));
        List<WebElement> passwords = driver.findElements(By.className("credentialPassword"));
        List<Credential> creds = new ArrayList<>();
        for (int i = 0; i < urls.size(); ++i) {
            try {
                Integer id = Integer.parseInt(ids.get(i).getAttribute("value"));
                String url = urls.get(i).getText();
                String username = usernames.get(i).getText();
                String password = passwords.get(i).getAttribute("value");
                Credential cred = Credential.builder()
                        .url(url).username(username).password(password)
                        .build();
                cred.setCredentialId(id);
                creds.add(cred);
            } catch (Exception e) {
            }
        }
        return creds;
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(500);
    }

    public boolean credPresent(Credential c, ArrayList<Credential> creds) {
        for (Credential cred : creds) {
            if (cred.getUrl().equals(c.getUrl())
                    && cred.getUsername().equals(c.getUsername()) && cred.getPassword().equals(c.getPassword()))
                return true;
        }
        return false;
    }

    public void updateCred(int credId, String url, String username, String password) throws InterruptedException {
        WebElement editCredButton = driver.findElement(By.id("upd" + credId));
        editCredButton.click();
        Thread.sleep(500);
        credUrl.clear();
        credUrl.sendKeys(url);
        credUsername.clear();
        credUsername.sendKeys(username);
        credPassword.clear();
        credPassword.sendKeys(password);
        credUrl.submit();
    }

    public Integer getCredId(String url, String username, String password, List<Credential> creds) {
        for (Credential cred : creds)
            if (cred.getUrl().equals(url)
                    && cred.getUsername().equals(username) && cred.getPassword().equals(password))
                return cred.getCredentialId();
        return null;
    }

    public void deleteCred(Integer id) throws InterruptedException {
        WebElement delCredButton = driver.findElement(By.id("deleteCrd" + id));
        delCredButton.click();
        sleep();
    }
}
