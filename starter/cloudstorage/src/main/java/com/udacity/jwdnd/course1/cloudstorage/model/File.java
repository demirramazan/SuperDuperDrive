package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    private Integer fileId;

    private String fileName;

    private String contentType;

    private  String fileSize;

    private Integer userId;

    private byte[] fileData;
}
