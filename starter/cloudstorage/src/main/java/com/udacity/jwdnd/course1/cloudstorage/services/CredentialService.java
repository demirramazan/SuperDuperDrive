package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UtilService utilService;

    public List<Credential> getCredentials(Integer userId) {
        List<Credential> credentials = new ArrayList<>();
        credentialMapper.getCredentials(userId).forEach(credential -> {
            Credential credential1 = Credential.builder().credentialId(credential.getCredentialId())
                    .url(credential.getUrl())
                    .username(credential.getUsername())
                    .password(encryptionService.decryptValue(credential.getPassword(), credential.getKey()))
                    .build();
            credentials.add(credential1);
        });
        return credentials;
    }

    public int addCredential(Credential credential) {
        String encodeKey = utilService.encodeValue();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodeKey);
        credential.setKey(encodeKey);
        credential.setPassword(encryptedPassword);
        return credentialMapper.insertCredential(credential);
    }

    public void updateCredential(Integer credentialId, String url, String username, String password) {
        Credential credential = credentialMapper.getCredential(credentialId);
        String encryptedPassword = encryptionService.encryptValue(password, credential.getKey());
        credentialMapper.updateCredential(credentialId, url, username, encryptedPassword);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
