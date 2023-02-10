package com.samsara.paladin.service.avatar;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.samsara.paladin.model.Avatar;

public interface AvatarService {

    void assignAvatarToUser(String username, MultipartFile file);

    Optional<Avatar> loadAvatarByUser(String username);
}
