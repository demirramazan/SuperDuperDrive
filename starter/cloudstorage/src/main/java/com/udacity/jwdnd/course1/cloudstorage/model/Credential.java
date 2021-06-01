package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credential {
    private Integer credentialId;

    private String url;

    private String username;

    private String key;

    private String password;

    private Integer userId;

    private String decryptPassword;
}
