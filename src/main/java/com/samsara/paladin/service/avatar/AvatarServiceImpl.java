package com.samsara.paladin.service.avatar;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;
import com.samsara.paladin.events.CustomEventPublisher;
import com.samsara.paladin.exceptions.user.UserNotFoundException;
import com.samsara.paladin.model.Avatar;
import com.samsara.paladin.repository.AvatarRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class AvatarServiceImpl implements AvatarService {

    private static final int NEW_IMAGE_HEIGHT = 128;

    private final AvatarRepository avatarRepository;

    private final UserRepository userRepository;

    private CustomEventPublisher eventPublisher;

    public AvatarServiceImpl(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEventPublisher(CustomEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void assignAvatarToUser(String username, MultipartFile file) {
        Avatar avatar;
        Optional<Avatar> optionalAvatar = avatarRepository.findAvatarByUser(username);

        if (optionalAvatar.isEmpty()) {
            avatar = new Avatar();
            avatar.setUser(userRepository.findByUsername(username)
                    .orElseThrow(
                            () -> new UserNotFoundException("User not found!")
                    ));
        } else {
            avatar = optionalAvatar.get();
        }
        addAvatarToUser(avatar, file);
    }

    @Override
    public Optional<Avatar> loadAvatarByUser(String username) {
        if (!userRepository.existsByUsername(username)) {
            return Optional.empty();
        }
        return avatarRepository.findAvatarByUser(username);
    }

    private void addAvatarToUser(Avatar avatar, MultipartFile file) {

        byte[] resized = resizeImage(file);

        avatar.setImageName(file.getOriginalFilename());
        avatar.setImageType(file.getContentType());
        avatar.setImage(resized);

        avatarRepository.save(avatar);
        eventPublisher.publishEvent(
                EventCategory.USER,
                avatar.getUser().getUsername(),
                EventAction.ADD_AVATAR
        );
    }

    private byte[] resizeImage(MultipartFile file) {
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            String extension = FilenameUtils.getExtension(
                    Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()
            );
            BufferedImage resized = resizeImage(bi, extension);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resized, extension, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("There was an error dealing with file!");
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, String extension) {
        int targetWidth = getWidthToPreserveScalability(originalImage);
        Image resultingImage =
                originalImage.getScaledInstance(
                        targetWidth,
                        NEW_IMAGE_HEIGHT,
                        Image.SCALE_DEFAULT
                );
        int typeInt = ("png".equals(extension))
                ? BufferedImage.TYPE_INT_ARGB
                : BufferedImage.TYPE_INT_RGB;

        BufferedImage outputImage = new BufferedImage(targetWidth, NEW_IMAGE_HEIGHT, typeInt);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    private int getWidthToPreserveScalability(BufferedImage originalImage) {
        float originalImageHeight = originalImage.getHeight();
        float ratio = NEW_IMAGE_HEIGHT / originalImageHeight;
        return (int) (ratio * originalImage.getWidth());
    }
}
