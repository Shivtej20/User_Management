package com.example.User_Management.Util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private Integer userID;
	private String uName;
	private String email;
	private String pwd;
	private String updatePwd;
	private String newPwd;
	private String confirmPwd;
	private Long phno;
	private Integer cID;
	private Integer sID;
	private Integer ctID;

}
