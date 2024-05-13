package com.example.User_Management.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CountryDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cID;
	private String cName;
	
	@OneToMany(mappedBy = "countryDtlsEntity", cascade = CascadeType.ALL)
	private List<StateDtlsEntity> states;
	
//	@OneToMany(mappedBy = "countryDtlsEntity")
//	private UserDtlsEntity userDtlsEntity;
	

}
