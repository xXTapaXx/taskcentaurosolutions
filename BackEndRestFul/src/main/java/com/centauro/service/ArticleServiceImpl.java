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
import com.centauro.model.ArticleModel;
import com.centauro.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Resource
	private ArticleRepository articleRepository;

	@Override
	@Transactional
	public ArticleModel create(ArticleModel articleModel) {
		ArticleModel createdArticle = articleModel;
		return articleRepository.save(createdArticle);
	}
	

	@Override
	@Transactional
	public ArticleModel findById(int id) {
		return articleRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ArticleModel delete(int id) throws ShopNotFound {
		ArticleModel deleteArticle = articleRepository.findOne(id);
		
		if (deleteArticle == null)
			throw new ShopNotFound();
		
		articleRepository.delete(deleteArticle);
		return deleteArticle;
	}
	
	

	@Override
	@Transactional
	public List<ArticleModel> findAll() {
		return articleRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public ArticleModel update(ArticleModel articleModel) throws ShopNotFound {
		ArticleModel updateArticle = articleRepository.findOne(articleModel.getId());
		
		if (updateArticle == null)
			throw new ShopNotFound();
		
		updateArticle.setTitle(articleModel.getTitle());
		updateArticle.setAlias(articleModel.getAlias());
		updateArticle.setPublish(articleModel.getPublish());
		updateArticle.setUser_update(articleModel.getUser_update());
		
		return updateArticle;
	}

}
