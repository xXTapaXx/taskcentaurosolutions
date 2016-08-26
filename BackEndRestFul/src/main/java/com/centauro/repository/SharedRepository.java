package com.centauro.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;




public interface SharedRepository extends JpaRepository<SharedModel, Integer> {
	
	 /* @Query("select u from users u where u.username = :username")
	  public User findByUsername(String username);*/
	 
	/*@Query("SELECT u FROM UserModel u WHERE u.username = :username")
	    public UserModel findByUsername(@Param("username") String username);*/
	@Query("SELECT s FROM SharedModel s WHERE s.email = :email AND s.shared_task_id = :shared_task_id")
		public SharedModel findByEmailAndSharedTask(@Param("email") String email, @Param("shared_task_id") TaskModel shared_task_id);
	
	@Query("SELECT s FROM SharedModel s WHERE s.list_id = :list_id ")
		//public SharedModel findByListId(@Param("list_id") String list_id);
	public List<SharedModel> findByListId(@Param("list_id") String list_id);
	
	@Query("SELECT s FROM SharedModel s WHERE s.task_id = :task_id")
	public SharedModel findByTaskId(@Param("task_id") String task_id);
	
	@Query("SELECT DISTINCT(s) FROM SharedModel s WHERE s.shared_list_id = :shared_list_id ORDER BY s.email")
	public List<SharedModel> findBySharedListId(@Param("shared_list_id") ListModel shared_list_id);
	
	@Query("SELECT s FROM SharedModel s INNER JOIN s.shared_list_id l INNER JOIN s.shared_task_id t  where s.email = :email")
	  public List<SharedModel> getSync(@Param("email") String email);
	
	@Query("SELECT s FROM SharedModel s WHERE s.shared_list_id = :shared_list_id AND email != :email")
	  public List<SharedModel> updateSharedSync(@Param("shared_list_id") ListModel shared_list_id,@Param("email") String email);
	
	
	

	
	
}
