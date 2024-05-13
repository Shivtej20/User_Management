package com.example.User_Management.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.User_Management.Entity.StateDtlsEntity;

public interface StateRepo extends JpaRepository<StateDtlsEntity, Integer>{
	
	@Query(
			value= "select * from StateDtlsEntity where cId = :cId",
			nativeQuery = true
			)
	public List<StateDtlsEntity> getStateList(Integer cId);

}
