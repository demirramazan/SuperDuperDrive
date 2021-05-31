package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UtilService utilService;

    @PostMapping
    public String addOrUpdateCredential(Credential credential, Model model) {
        int userid = utilService.getUserId();
        if (credential.getCredentialId() != null) {
            credentialService.updateCredential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), credential.getPassword());
        } else {
            credential.setUserId(userid);
            credentialService.addCredential(credential);
        }
        List<Credential> credentialList = credentialService.getCredentials(userid);
        model.addAttribute("credentials", credentialList);
        model.addAttribute("message", "Credential added/updated successfully!");
        return "home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable Integer credentialId, Model model) {
        int userid = utilService.getUserId();
        credentialService.deleteCredential(credentialId);
        List<Credential> credentials = credentialService.getCredentials(userid);
        model.addAttribute("credentials", credentials);
        model.addAttribute("message", "Credential deleted successfully!");
        return "home";
    }
}
