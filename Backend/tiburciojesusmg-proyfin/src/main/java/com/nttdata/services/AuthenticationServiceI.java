package com.nttdata.services;

import com.nttdata.dao.request.SignUpRequest;
import com.nttdata.dao.request.SigninRequest;
import com.nttdata.dao.response.JwtAuthenticationResponse;

public interface AuthenticationServiceI {

	JwtAuthenticationResponse signup(SignUpRequest request);

	JwtAuthenticationResponse signin(SigninRequest request);

}
