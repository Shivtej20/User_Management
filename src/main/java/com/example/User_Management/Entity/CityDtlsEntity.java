package com.example.User_Management.Entity;

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
public class CityDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ctID;
	private String ctName;
	
	
	@ManyToOne
	@JoinColumn(name ="sID")
	private StateDtlsEntity stateDtlsEntity;
	
//	@OneToMany(mappedBy = "cityDtlsEntity")
//	private UserDtlsEntity userDtlsEntity;

}
