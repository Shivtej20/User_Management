package com.example.User_Management.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.User_Management.Entity.CityDtlsEntity;

public interface CityRepo extends JpaRepository<CityDtlsEntity, Integer>{
	
	@Query(
			value ="select * from CityDtlsEntity where sID=:sID",
			nativeQuery = true
			)
	public List<CityDtlsEntity> getcityList(Integer sID);

}
