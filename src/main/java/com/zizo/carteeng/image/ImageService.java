package com.zizo.carteeng.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zizo.carteeng.members.MemberRepository;
import com.zizo.carteeng.members.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final MemberRepository memberRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveImage(Long memberId, MultipartFile multipartFile) throws IOException {
        String uploadString = upload(multipartFile);

        String imageUrl = getImageUrl(uploadString);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("not found member"));
        member.updateImage(imageUrl);
        return imageUrl;
    }

    public String getImageUrl(String uploadString) {
        URL url = amazonS3.getUrl(bucket, uploadString);
        return ""+url;
    }

    private String upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return s3FileName;
    }
}
