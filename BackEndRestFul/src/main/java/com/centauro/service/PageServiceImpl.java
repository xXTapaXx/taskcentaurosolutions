package com.centauro.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.MenuModel;
import com.centauro.model.PageModel;
import com.centauro.repository.MenuRepository;
import com.centauro.repository.PageRepository;
import com.centauro.views.MenuView;
import com.centauro.views.PageView;

@Service
public class PageServiceImpl implements PageService {
	
	@Resource
	private PageRepository pageRepository;

	@Override
	@Transactional
	public PageModel create(PageModel page) {
		PageModel createdPage = page;
		return pageRepository.save(createdPage);
	}
	
	
	/*@Override
	@Transactional
	public List<PageView> getShowPage(String title, String alias){
		List<Page> listPage = pageRepository.getShowPage(title,alias);
		List<PageView> pageView = new ArrayList();
		for(PageView page : pageView){
			pageView.add(new PageView(page.getId(),page.getTitle(),page.getAlias(),page.getType(),page.getId_type(),page.getPosition(),page.getItem()));		
			
		}
		
		return pageView;
	}
	*/
	
	@Override
	@Transactional
	public List<PageModel> getShowPage(String title, String alias){
		
		return pageRepository.getShowPage(title,alias);
	}
	
	
	

	@Override
	@Transactional
	public PageModel findById(int id) {
		return pageRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public PageModel delete(int id) throws ShopNotFound {
		PageModel deletePage = pageRepository.findOne(id);
		
		if (deletePage == null)
			throw new ShopNotFound();
		
		pageRepository.delete(deletePage);
		return deletePage;
	}
	
	

	@Override
	@Transactional
	public List<PageModel> findAll() {
		return pageRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public PageModel update(PageModel page) throws ShopNotFound {
		PageModel updatePage = pageRepository.findOne(page.getId());
		
		if (updatePage == null)
			throw new ShopNotFound();
		
		updatePage.setTitle(page.getTitle());
		updatePage.setAlias(page.getAlias());
		updatePage.setType(page.getType());
		updatePage.setId_type(page.getId_type());
		updatePage.setPosition(page.getPosition());
		
		return updatePage;
	}

}
