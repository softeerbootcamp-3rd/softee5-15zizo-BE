package com.zizo.carteeng.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ImageRequest {
    MultipartFile multipartFile;
}
