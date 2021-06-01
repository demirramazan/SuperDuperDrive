package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credentials")
@Slf4j
public class CredentialController {
    private final CredentialService credentialService;
    private final UtilService utilService;

    @PostMapping
    public String addOrUpdateCredential(Credential credential, RedirectAttributes redirectAttributes) {
        int userid = utilService.getUserId();
        try {
            if (credential.getCredentialId() != null) {
                credentialService.updateCredential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), credential.getPassword());
                log.info("Credential updated successfully");
                redirectAttributes.addFlashAttribute("successMessage", "Credential updated successfully!");
            } else {
                credential.setUserId(userid);
                credentialService.addCredential(credential);
                log.info("Credential added successfully");
                redirectAttributes.addFlashAttribute("successMessage", "Credential added successfully!");
            }
        } catch (Exception e) {
            log.error("Error occured: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Credential added/updated error occured!");
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deletCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("successMessage", "Credential deleted successfully!");
        return "redirect:/home";
    }
}
