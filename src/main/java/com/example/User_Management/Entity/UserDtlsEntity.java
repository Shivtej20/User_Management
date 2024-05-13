package com.example.User_Management.Entity;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserDtlsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userID;
	private String uName;
	private String email;
	private String pwd;
	private String updatePwd; 
	private Long phno;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cID")
	private CountryDtlsEntity countryDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="sID")
	private StateDtlsEntity stateDtlsEntity;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "ctID")
	private CityDtlsEntity cityDtlsEntity;

}
