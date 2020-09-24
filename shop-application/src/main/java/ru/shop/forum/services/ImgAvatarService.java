package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ImgAvatarService extends AbstractForumEntityService<ImgAvatar, ForumEntityRepository<ImgAvatar>> {
	
	public ImgAvatarService(ForumEntityRepository<ImgAvatar> repository) {
		super(repository);
		this.entityClass = ImgAvatar.class;
	}
}
