package com.travel.wifimap.web.controller;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.travel.wifimap.domain.User;
import com.travel.wifimap.json.entity.login.LoginResult;
import com.travel.wifimap.json.entity.login.Status;
import com.travel.wifimap.service.UserService;
import com.travel.wifimap.util.StringUtils;

@Controller
public class LoginController {

	private DESKeySpec keySpec;
	private SecretKeyFactory keyFactory;
	private SecretKey key;
	{
		try {
			keySpec = new DESKeySpec("SecreCod".getBytes("UTF8"));
			keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
		}
	}

	@Autowired
	private UserService userService;

	private Gson gson = new Gson();

	@RequestMapping(value = { "/signin", "/login" })
	@ResponseBody
	public String login(@RequestParam String email,
			@RequestParam String password, HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		LoginResult result = new LoginResult();
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
			result.setStatus(Status.ERROR);
			result.setError_message("Parameter missed");
			// System.out.println("Parameter missed");
		} else {
			User user = userService.findUserByEmail(email);
			if (user == null) {
				result.setStatus(Status.ERROR);
				result.setError_message("User with email " + email
						+ " is not registered");
				// System.out.println("User with email " + email
				// + " is not registered");
			} else {
				String pass = user.getPassword();
				if (StringUtils.isEmpty(pass)) {
					result.setStatus(Status.ERROR);
					result.setError_message("Password missed");
					// System.out.println("Password missed");
				} else {
					String encrypedPassword = encript(password);
					if (encrypedPassword.equals(pass)) {
						result.setStatus(Status.OK);
						result.setUser_id(user.getId());
						// System.out.println("OK");
					} else {
						result.setStatus(Status.ERROR);
						result.setError_message("Wrong password");
						// System.out.println("Wrong password");
					}
				}
			}
		}
		return gson.toJson(result);
	}

	@RequestMapping(value = { "/signup", "/reg" })
	@ResponseBody
	public String reg(@RequestParam String email,
			@RequestParam String password, HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// System.out.println("reg start");
		// System.out.println("email = " + email);
		// System.out.println("password = " + password);
		LoginResult result = new LoginResult();
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
			result.setStatus(Status.ERROR);
			result.setError_message("Parameter missed");
		} else {
			User user = userService.findUserByEmail(email);
			if (user != null) {
				// System.out.println("user != null");
				result.setStatus(Status.ERROR);
				result.setError_message("There are no register parameters");
			} else {
				// System.out.println("user == null");
				String encrypedPassword = encript(password);
				// System.out.println("encrypedPassword = " + encrypedPassword);
				if (StringUtils.isEmpty(encrypedPassword)) {
					result.setStatus(Status.ERROR);
					result.setError_message("Internal Error");
				} else {
					user = new User();
					user.setEmail(email);
					user.setPassword(encrypedPassword);
					userService.saveUser(user);
					result.setStatus(Status.OK);
					result.setUser_id(user.getId());
				}
			}
		}
		return gson.toJson(result);
	}

	private String encript(String password) {
		String encrypedPassword = null;
		try {
			byte[] cleartext = password.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypedPassword = Base64.encodeBase64String(cipher
					.doFinal(cleartext));
		} catch (Exception e) {
		}
		return encrypedPassword;
	}
}