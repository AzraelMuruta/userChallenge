package com.movchynnykov.userDataChallenge.dto.request;


import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.movchynnykov.userDataChallenge.Utils.Constantes;
import com.movchynnykov.userDataChallenge.dto.PhoneDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
	
	private String name;
	
	@NotBlank(message = Constantes.errorMailVacio)
	@Pattern(regexp="^[a-z0-9][\\w\\.]+@\\w+(\\.\\w+){1,}$", flags = { Flag.CASE_INSENSITIVE}, message = Constantes.errorMailFormatoIncorrecto)
	private String email;
	
	@NotBlank(message = Constantes.errorPassVacio)
	@Pattern(regexp="^(?=(?:[^A-Z]*[A-Z]){0,1}[^A-Z]*$)(?=(?:[^0-9]*[0-9]){0,2}[^0-9]*$)[a-zA-Z0-9]{8,12}$", message = Constantes.errorPassFormatoIncorrecto)
	private String password;
	
	private List<PhoneDto> phones;
}
