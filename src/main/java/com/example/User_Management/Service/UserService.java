package com.example.User_Management.Service;

import java.util.Map;

import com.example.User_Management.Util.LogInDTO;
import com.example.User_Management.Util.RegisterDto;
import com.example.User_Management.Util.ResetPwdDto;
import com.example.User_Management.Util.UserDto;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer cID);

	public Map<Integer, String> getCities(Integer sID);

	public UserDto getUser(String email);

	public boolean registerUser(RegisterDto registerDto);

	public UserDto getUser(LogInDTO logInDTO);

	public boolean resetPwd(ResetPwdDto resetPwdDto);

	public String getQuote();

}
