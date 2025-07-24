package com.movchynnykov.userDataChallenge.services.implementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movchynnykov.userDataChallenge.Utils.Constantes;
import com.movchynnykov.userDataChallenge.Utils.JwtUtils;
import com.movchynnykov.userDataChallenge.Utils.PasswordUtils;
import com.movchynnykov.userDataChallenge.dto.PhoneDto;
import com.movchynnykov.userDataChallenge.dto.UserDto;
import com.movchynnykov.userDataChallenge.dto.request.LoginRequest;
import com.movchynnykov.userDataChallenge.dto.request.SignUpRequest;
import com.movchynnykov.userDataChallenge.dto.response.ErrorResponse;
import com.movchynnykov.userDataChallenge.dto.response.UserResponse;
import com.movchynnykov.userDataChallenge.model.Phone;
import com.movchynnykov.userDataChallenge.model.User;
import com.movchynnykov.userDataChallenge.repository.UserRepository;
import com.movchynnykov.userDataChallenge.services.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepo;
    
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public UserResponse signUpUser(SignUpRequest user) throws Exception {
		UserResponse response = null;
		
		//ver si el usuario ya existe en base a su email. Si ya existe, tirar excepcion
		User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
		if(existingUser == null) {
			
			//Generar UUID usando mail del usuario
			UUID uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes());
	        String userUUID = uuid.toString();
	        
			//Hash password
			byte[] salt = PasswordUtils.getNewSalt();
			byte[] hashedPassword = PasswordUtils.hashPassword(user.getPassword(), salt);
			
			//Generar token usando JWT y el mail del usuario
			String token = jwtUtils.generateToken(user.getEmail());
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			
			//armar telefonos
			List<Phone> phoneList = new ArrayList<Phone>();
			if(user.getPhones() != null) {
				for(PhoneDto p : user.getPhones()) {
					Phone phone = Phone.builder().uuid(userUUID).number(p.getNumber()).citycode(p.getCitycode()).countrycode(p.getCountrycode()).build();
					phoneList.add(phone);
				}
			}

			//Armar user y guardar en repo
			User newUser = User.builder().uuid(userUUID).phones(phoneList).name(user.getName()).email(user.getEmail()).salt(salt).password(hashedPassword).isActive(true).token(token).created(currentTime).build();
			userRepo.save(newUser);
			
			//respuesta
			UserDto userDto = UserDto.builder().id(userUUID).created(currentTime).lastLogin(currentTime).token(token).isActive(true).build();
			response = UserResponse.builder().status(200).message(Constantes.statusSuccess).body(userDto).build();
		} else {
			ErrorResponse error = new ErrorResponse(new Timestamp(System.currentTimeMillis()), 403);
			error.setDetail(Constantes.errorUsuarioYaExiste);
			response = UserResponse.builder().status(403).message(Constantes.errorForbidden).body(error).build();
		}
		
		return response;
	}

	@Override
	public UserResponse login(LoginRequest user) throws Exception {
		UserResponse response = null;
		
		//obtener mail desde el token y buscar usuario
		String userEmail = jwtUtils.parseTokenForEmail(user.getToken());
		User existingUser = userRepo.findByEmail(userEmail).orElse(null);
		//Verificar si el usuario existe, y el token ingresado es el ultimo token generado para este
		if(existingUser != null && user.getToken().equals(existingUser.getToken())) {
			//Generar nuevo token usando JWT y el mail del usuario
			String token = jwtUtils.generateToken(userEmail);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			
			//generar respuesta
			UserDto userDto = UserDto.builder().id(existingUser.getUuid()).created(existingUser.getCreated()).lastLogin(existingUser.getLastLogin()).token(token).phones(existingUser.getPhones()).isActive(existingUser.isActive()).build();
			response = UserResponse.builder().status(200).message(Constantes.statusSuccess).body(userDto).build();
			
			//actualizar token y tiempo de last login del usuario
			existingUser.setToken(token);
			existingUser.setLastLogin(currentTime);
			userRepo.save(existingUser);
		} else {
			ErrorResponse error = new ErrorResponse(new Timestamp(System.currentTimeMillis()), 403);
			error.setDetail(Constantes.errorUsuarioInexistente);
			response = UserResponse.builder().status(403).message(Constantes.errorForbidden).body(error).build();
		}
		
		return response;
	}
}
