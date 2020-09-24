package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.ImgAvatarRepository;

@Service
public class ImgAvatarService extends AbstractForumEntityService<ImgAvatar, ImgAvatarRepository> {
	
	public ImgAvatarService(ImgAvatarRepository repository) {
		super(repository);
		this.entityClass = ImgAvatar.class;
	}
}
