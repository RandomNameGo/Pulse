package com.app.pulse.services;

import com.app.pulse.dto.request.LoginRequest;
import com.app.pulse.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse Login(LoginRequest loginRequest);

}
