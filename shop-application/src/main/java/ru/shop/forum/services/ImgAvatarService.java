package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ImgAvatarService extends AbstractForumEntityService<ImgAvatar, ForumEntityRepository<ImgAvatar>> {
	@Override
	protected void setRepository(ForumEntityRepository<ImgAvatar> repository) {
		this.repository = repository;
	}
}
