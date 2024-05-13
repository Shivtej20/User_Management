package com.example.User_Management.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StateDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sID;
	private String sName;
	
	@ManyToOne
	@JoinColumn(name = "cID")
	private CountryDtlsEntity countryDtlsEntity;
	
	
	@OneToMany(mappedBy = "stateDtlsEntity", cascade = CascadeType.ALL)
	private List<CityDtlsEntity> cities;
	
//	@OneToMany(mappedBy = "stateDtlsEntity")
//	private UserDtlsEntity userDtlsEntity;

}
