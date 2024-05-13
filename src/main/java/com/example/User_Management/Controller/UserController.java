package com.example.User_Management.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.User_Management.Service.UserServiceImpl;
import com.example.User_Management.Util.LogInDTO;
import com.example.User_Management.Util.RegisterDto;
import com.example.User_Management.Util.ResetPwdDto;
import com.example.User_Management.Util.UserDto;

@Controller
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/register")
	public String registerPage(Model model) {

		model.addAttribute("registerDto", new RegisterDto());
		// Map<Integer, String> countries = serviceImpl.getCountries();
		model.addAttribute("countries", userServiceImpl.getCountries());
		return "registerView";
	}

	@GetMapping("/states/{cID}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable("cID") Integer cID) {
		return userServiceImpl.getStates(cID);
	}

	@GetMapping("/cities/{sID}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable("sID") Integer sID) {
		return userServiceImpl.getCities(sID);
	}

	@PostMapping("/register")
	public String HandleRegister(Model model, RegisterDto registerDto) {
		UserDto user = userServiceImpl.getUser(registerDto.getEmail());
		if (user != null) {
			model.addAttribute("emsg", "Duplicate Email");
			return "registerView";
		}
		boolean registerUser = userServiceImpl.registerUser(registerDto);
		if (registerUser) {
			model.addAttribute("smsg", "User Registered succeessfully");
		} else {
			model.addAttribute("emsg", "Registration failed");
		}
		return "registerView";
	}

	@GetMapping("/")
	public String getLogin(Model model) {
		model.addAttribute("loginDto", new LogInDTO());
		return "index";
	}

	@PostMapping("/login")
	public String login(Model model, LogInDTO logInDTO) {
		UserDto user = userServiceImpl.getUser(logInDTO);
		if (user == null) {
			model.addAttribute("emsg", "Invalid credantials");
			return "index";
		}
		String pwd = user.getUpdatePwd();
		if ("Yes".equals(pwd)) {
			return "redirect:dashbord";
		} else {
			ResetPwdDto resetPwdDto = new ResetPwdDto();
			resetPwdDto.setEmail(user.getEmail());
			model.addAttribute("resetPwdDto", resetPwdDto);
			return "resetPwdView";
		}
	}

	@PostMapping("/resetPwd")
	public String resetPwd(ResetPwdDto pwdDto, Model model) {
		if (!(pwdDto.getNewPwd().equals(pwdDto.getConfirmPwd()))) {
			model.addAttribute("emsg", "Both password should be same");
			return "resetPwdView";
		}

		UserDto user = userServiceImpl.getUser(pwdDto.getEmail());
		if (user.getPwd().equals(pwdDto.getPwd())) {
			boolean resetPwd = userServiceImpl.resetPwd(pwdDto);
			if (resetPwd) {
				return "redirect:dashbord";
			} else {
				model.addAttribute("emsg", "Password update failed");
				return "resetPwdView";
			}

		} else {
			model.addAttribute("emsg", "Password incorrecet");
			return "resetPwdView";
		}
	}

	@GetMapping("/dashbord")
	public String getDashboard(Model model) {

		String quote = userServiceImpl.getQuote();
		model.addAttribute("quote", quote);
		return "dashbordView";
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}

}
