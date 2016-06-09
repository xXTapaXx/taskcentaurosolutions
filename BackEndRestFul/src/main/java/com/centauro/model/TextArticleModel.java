package com.centauro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "text_article")
public class TextArticleModel {

	@Id
	@GeneratedValue
	private Integer id;

	private String text_article;

	private int article_id;
	
	private int order_by;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText_article() {
		return text_article;
	}

	public void setText_article(String text_article) {
		this.text_article = text_article;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public int getOrder_by() {
		return order_by;
	}

	public void setOrder_by(int order_by) {
		this.order_by = order_by;
	}
	
}
