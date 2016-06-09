package com.centauro.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
public class ArticleModel {

	@Id
	@GeneratedValue
	private Integer id;

	private String title;

	private String alias;
	
	private String text_article;
	
	private String publish;
	
	private Integer user_create;
	
	private Timestamp create_at;
	
	private Integer user_update;
			
	private Timestamp update_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getText_article() {
		return text_article;
	}

	public void setText_article(String text_article) {
		this.text_article = text_article;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public Integer getUser_create() {
		return user_create;
	}

	public void setUser_create(Integer user_create) {
		this.user_create = user_create;
	}

	public Timestamp getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}

	public Integer getUser_update() {
		return user_update;
	}

	public void setUser_update(Integer user_update) {
		this.user_update = user_update;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
	}

	
	
	
	
	
}
