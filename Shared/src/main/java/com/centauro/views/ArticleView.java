package com.centauro.views;

import java.sql.Timestamp;

public class ArticleView {

    private final long id;
    private final String title;
    private final String alias;
    private final String text_article;
    private final String publish;
    private final Integer user_create;
    private final Timestamp create_at;
    private final Integer user_update;
    private final Timestamp update_at;
    
    public ArticleView(){
    	id = 0;
    	title = null;
    	alias = null;
    	text_article = null;
    	publish = null;
    	user_create = 0;
    	create_at = null;
    	user_update = 0;
    	update_at = null;
    }
    
    public ArticleView(long id, String title,String alias,String text_article,String publish, Integer user_create, Timestamp create_at, Integer user_update, Timestamp update_at) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.text_article = text_article;
        this.publish = publish;
        this.user_create = user_create;
        this.create_at = create_at;
        this.user_update = user_update;
        this.update_at = update_at;
    }

   
    
    public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAlias() {
		return alias;
	}
	
	public String getText_article(){
		return text_article;
	}

	public String getPublish() {
		return publish;
	}

	public long getUser_create() {
		return user_create;
	}

	public Timestamp getCreate_at() {
		return create_at;
	}

	public long getUser_update() {
		return user_update;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	@Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", title='" + title + 
                ", alias='" + alias + 
                ", text_article='" + text_article +
                ", publish='" + publish + 
                ", user_create='" + user_create + 
                ", create_at='" + create_at + 
                ", user_update='" + user_update + 
                ", update_at='" + update_at + '\'' +
                '}';
    }
}