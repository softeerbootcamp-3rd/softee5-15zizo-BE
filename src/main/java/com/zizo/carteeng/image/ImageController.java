
package com.zizo.carteeng.image;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.members.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/img")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final MemberService memberService;

    //내 사진 올리기
    @PostMapping
    public ResponseEntity<String> saveImage(HttpServletRequest request, @ModelAttribute MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        Long memberId = (Long)session.getAttribute("member_id");

        String imageUrl =  imageService.saveImage(memberId, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    }

    //남의 사진 보기
    @GetMapping("/{memberId}") //눌러진 멤버
    public ResponseEntity<String> getImage(@PathVariable Long memberId) {
        String imageUrl = memberService.findById(memberId).getImageUrl();
        if(imageUrl==null)
            throw new ErrorException(ErrorCode.IMAGE_NOT_FOUND);
        return ResponseEntity.ok().body(imageUrl);
    }





}