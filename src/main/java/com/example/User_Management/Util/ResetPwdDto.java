package com.example.User_Management.Util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwdDto {
	
	private Integer userId;
	private String email;
	private String pwd; 
	private String newPwd; 
	private String confirmPwd; 

}
