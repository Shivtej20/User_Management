package com.example.User_Management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.User_Management.Entity.UserDtlsEntity;

public interface UserRepo extends JpaRepository<UserDtlsEntity, Integer>{
	
	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByEmailAndPwd(String email, String pwd);

}
