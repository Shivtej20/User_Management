package com.example.User_Management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.User_Management.Entity.CountryDtlsEntity;

public interface CountryRepo extends JpaRepository<CountryDtlsEntity, Integer>{

}
