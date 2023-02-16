package com.samsara.paladin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samsara.paladin.configuration.validation.user.username.Username;
import com.samsara.paladin.model.Avatar;
import com.samsara.paladin.service.avatar.AvatarServiceImpl;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/avatars")
@Validated
public class AvatarController {

    @Autowired
    private AvatarServiceImpl avatarService;

    @PostMapping("/new")
    public ResponseEntity<String> assignNewAvatarToUser(
            Authentication authentication,
            @RequestParam("image") @NotNull MultipartFile file
    ) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Must upload file!", HttpStatus.BAD_REQUEST);
        }
        avatarService.assignAvatarToUser(authentication.getName(), file);
        return new ResponseEntity<>("Avatar saved!", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/username/{username}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody ResponseEntity<byte[]> readAvatarFromUser(
            @PathVariable("username")
            @Username
            String username
    ) {

        Optional<Avatar> optionalAvatar = avatarService.loadAvatarByUser(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (optionalAvatar.isEmpty()) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        Avatar avatar = optionalAvatar.get();

        headers.add("image-name", avatar.getImageName());
        headers.add("image-type", avatar.getImageType());
        if ("png".equals(avatar.getImageType())) {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<>(avatar.getImage(), headers, HttpStatus.OK);
    }
}
