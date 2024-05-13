package com.example.User_Management.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.User_Management.Entity.CityDtlsEntity;
import com.example.User_Management.Entity.CountryDtlsEntity;
import com.example.User_Management.Entity.StateDtlsEntity;
import com.example.User_Management.Entity.UserDtlsEntity;
import com.example.User_Management.Repo.CityRepo;
import com.example.User_Management.Repo.CountryRepo;
import com.example.User_Management.Repo.StateRepo;
import com.example.User_Management.Repo.UserRepo;
import com.example.User_Management.Util.EmailUtils;
import com.example.User_Management.Util.LogInDTO;
import com.example.User_Management.Util.QuoteDto;
import com.example.User_Management.Util.RegisterDto;
import com.example.User_Management.Util.ResetPwdDto;
import com.example.User_Management.Util.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private CountryRepo  countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Map<Integer, String> getCountries() {
		
		Map<Integer, String> countryMap = new HashMap<>();
		
		List<CountryDtlsEntity> countryList = countryRepo.findAll();
		countryList.forEach(c -> {countryMap.put(c.getCID(), c.getCName());
			});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer cID) {
		
		Map<Integer, String> stateMap = new HashMap<>();
		/*
		This is first approach to get state list with cId or we have write one native SQL query to find list
		
		CountryDtlsEntity country = new CountryDtlsEntity();
		country.setCID(cID);
		
		StateDtlsEntity state = new StateDtlsEntity();
		state.setCountryDtlsEntity(country);
		
		Example<StateDtlsEntity> of = Example.of(state);
		List<StateDtlsEntity> stateList = stateRepo.findAll(of);
		*/
		
		List<StateDtlsEntity> stateList = stateRepo.getStateList(cID);
		stateList.forEach(s->{
			stateMap.put(s.getSID(), s.getSName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer sID) {
		Map<Integer, String> cityMap= new HashMap<>();
		List<CityDtlsEntity> cityList = cityRepo.getcityList(sID);
		
		cityList.forEach(c->{
			cityMap.put(c.getCtID(), c.getCtName());
		});
		return cityMap;
	}

	@Override
	public UserDto getUser(String email) {
		UserDtlsEntity findByEmail = userRepo.findByEmail(email);
		
		/*
		//First way to mapping object from one object to another object
		UserDto dto =new UserDto();
		BeanUtils.copyProperties(findByEmail, dto);
		*/
		
		//By using Model Mapper
		ModelMapper mapper= new ModelMapper();
		UserDto dto = mapper.map(findByEmail, UserDto.class);
		return dto;
	}

	@Override
	public boolean registerUser(RegisterDto registerDto) {
		ModelMapper mapper =new ModelMapper();
		UserDtlsEntity entity = mapper.map(registerDto, UserDtlsEntity.class);
		
		CountryDtlsEntity country = countryRepo.findById(registerDto.getCID()).orElseThrow();
		StateDtlsEntity state = stateRepo.findById(registerDto.getSID()).orElseThrow();
		CityDtlsEntity city = cityRepo.findById(registerDto.getCtID()).orElseThrow();
		
		entity.setCountryDtlsEntity(country);
		entity.setStateDtlsEntity(state);
		entity.setCityDtlsEntity(city);
		entity.setPwd(generateRandom());
		entity.setUpdatePwd("NO");
		
		UserDtlsEntity save = userRepo.save(entity);
		
		String subject ="User registration Successfully";
		String body ="Your temporary password is "+entity.getPwd();
		emailUtils.sendEmail(registerDto.getEmail(), subject, body );
		return save.getUserID()!=null;
	}

	@Override
	public UserDto getUser(LogInDTO logInDTO) {
		UserDtlsEntity entity = userRepo.findByEmailAndPwd(logInDTO.getEmail(), logInDTO.getPwd());
		
		if(entity == null){
			return null;
		}
		
		ModelMapper mapper =new ModelMapper();
		return mapper.map(entity, UserDto.class);
	}

	@Override
	public boolean resetPwd(ResetPwdDto resetPwdDto) {
		UserDtlsEntity entity = userRepo.findByEmailAndPwd(resetPwdDto.getEmail(), resetPwdDto.getPwd());
		
		if(entity != null) {
			entity.setPwd(resetPwdDto.getNewPwd());
			entity.setUpdatePwd("Yes");
			userRepo.save(entity);
			return true;
		}
		
		return false;
	}

	@Override
	public String getQuote() {
		
		QuoteDto[] quotations = null;
		String url ="https://type.fit/api/quotes";
		
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
		String body = forEntity.getBody();
		
		ObjectMapper mapper=new ObjectMapper();
		try {
			quotations = mapper.readValue(body, QuoteDto[].class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		Random r = new Random();
		int i = r.nextInt(quotations.length-1);
		return quotations[i].getText();
	}
	
	//Generate random string in java 
	private static String generateRandom() {
		String aToZ="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < 5; i++) {
	       int randIndex=rand.nextInt(aToZ.length()-1); 
	       res.append(aToZ.charAt(randIndex));            
	    }
	    return res.toString();
	}

}
