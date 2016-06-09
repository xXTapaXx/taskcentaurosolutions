package com.centauro.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.TextArticleModel;
import com.centauro.repository.TextArticleRepository;


@Service
public class TextArticleServiceImpl implements TextArticleService {
	
	@Resource
	private TextArticleRepository text_articleRepository;

	@Override
	@Transactional
	public TextArticleModel create(TextArticleModel text_article) {
		TextArticleModel createdText_Article = text_article;
		return text_articleRepository.save(createdText_Article);
	}
	

	@Override
	@Transactional
	public TextArticleModel findById(int id) {
		return text_articleRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public TextArticleModel delete(int id) throws ShopNotFound {
		TextArticleModel deletedText_Article = text_articleRepository.findOne(id);
		
		if (deletedText_Article == null)
			throw new ShopNotFound();
		
		text_articleRepository.delete(deletedText_Article);
		return deletedText_Article;
	}
	
	

	@Override
	@Transactional
	public List<TextArticleModel> findAll() {
		return text_articleRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public TextArticleModel update(TextArticleModel text_article) throws ShopNotFound {
		TextArticleModel updatedText_Article = text_articleRepository.findOne(text_article.getId());
		
		if (updatedText_Article == null)
			throw new ShopNotFound();
		
		updatedText_Article.setText_article(text_article.getText_article());
		updatedText_Article.setArticle_id(text_article.getArticle_id());
		updatedText_Article.setOrder_by(text_article.getOrder_by());
		return updatedText_Article;
	}

}
