package com.travel.wifimap.web.controller;

import static com.travel.wifimap.web.SessionAttributes.ATTR_OAUTH_ACCESS_TOKEN;
import static com.travel.wifimap.web.SessionAttributes.ATTR_OAUTH_REQUEST_TOKEN;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.travel.wifimap.OAuthServiceProvider;
import com.travel.wifimap.domain.SocialProfile;
import com.travel.wifimap.domain.User;
import com.travel.wifimap.json.entity.fb.FbUser;
import com.travel.wifimap.json.entity.fb.SimpleFbObject;
import com.travel.wifimap.service.UserService;
import com.travel.wifimap.util.StringUtils;

@Controller
public class FacebookController {

	@Autowired
	@Qualifier("facebookServiceProvider")
	private OAuthServiceProvider facebookServiceProvider;

	@Autowired
	private UserService userService;

	private static Token EMPTY_TOKEN = null;

	@RequestMapping(value = { "/login-facebook" }, method = RequestMethod.GET)
	public void login(WebRequest request, HttpServletResponse response) {

		// getting request and access token from session
		// Token accessToken = (Token) request.getAttribute(
		// ATTR_OAUTH_ACCESS_TOKEN, SCOPE_SESSION);
		Token accessToken = (Token) request.getAttribute(
				ATTR_OAUTH_ACCESS_TOKEN, SCOPE_REQUEST);
		if (accessToken == null) {
			accessToken = (Token) request.getAttribute(ATTR_OAUTH_ACCESS_TOKEN,
					SCOPE_REQUEST);
			if (accessToken != null) {
				request.setAttribute(ATTR_OAUTH_ACCESS_TOKEN, accessToken,
						SCOPE_REQUEST);
			}
		}
		if (accessToken == null) {
			// generate new request token
			OAuthService service = facebookServiceProvider.getService();
			// request.setAttribute(ATTR_OAUTH_REQUEST_TOKEN, EMPTY_TOKEN,
			// SCOPE_SESSION);
			request.setAttribute(ATTR_OAUTH_REQUEST_TOKEN, EMPTY_TOKEN,
					SCOPE_REQUEST);

			// redirect to facebook auth page
			String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
			int start = authorizationUrl.indexOf("www.facebook.com");
			StringBuffer buf = new StringBuffer(authorizationUrl.substring(0,
					start));
			buf.append("m");
			buf.append(authorizationUrl.substring(start + 3));
			String res = buf.toString();
			if (request.getAttribute("debug", SCOPE_REQUEST) != null
					|| request.getAttribute("debug", SCOPE_SESSION) != null) {
				res = authorizationUrl;
			}
			System.out.println("res = " + res);
			try {
				response.sendRedirect(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			OAuthService service = facebookServiceProvider.getService();
			// ?oauthAccessToken=TOKEN&expirationDate=UNIXTIMESTAMP&vendor=Apple&device=iPad&deviceVer=4S&os=iOS&osVer=5.1.0
			// json
			String result = getResult("json", service, accessToken);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				if (result != null) {
					pw.print(result);
				} else {
					pw.println("[]");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pw != null)
					pw.close();
			}
		}
	}

	@RequestMapping(value = { "/facebook-callback" }, method = RequestMethod.GET)
	public ModelAndView callback(
			@RequestParam(value = "code", required = false) String oauthVerifier,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error_reason", required = false) String errorReason,
			WebRequest request) {

		// getting request token
		OAuthService service = facebookServiceProvider.getService();
		// Token requestToken = (Token) request.getAttribute(
		// ATTR_OAUTH_REQUEST_TOKEN, SCOPE_SESSION);
		Token requestToken = (Token) request.getAttribute(
				ATTR_OAUTH_REQUEST_TOKEN, SCOPE_REQUEST);

		String result = null;
		if (StringUtils.isEmpty(error)) {
			// getting access token
			Verifier verifier = new Verifier(oauthVerifier);
			Token accessToken = service.getAccessToken(requestToken, verifier);
			// store access token as a session attribute
			// request.setAttribute(ATTR_OAUTH_ACCESS_TOKEN, accessToken,
			// SCOPE_SESSION);
			request.setAttribute(ATTR_OAUTH_ACCESS_TOKEN, accessToken,
					SCOPE_REQUEST);
			result = getResult(service, accessToken);
		} else {
			// ERROR
			// error_reason=user_denied&error=access_denied&error_description=The+user+denied+your+request.
			if ("access_denied".equals(error)
					&& "user_denied".equals(errorReason)) {
				result = formRedirectString("user_canceled");
			} else {
				result = formRedirectString("internal_error");
			}
		}

		ModelAndView mav = new ModelAndView("redirect:" + result);
		return mav;
	}

	private String getResult(OAuthService service, Token accessToken) {
		return getResult(null, service, accessToken);
	}

	private String getResult(String way, OAuthService service, Token accessToken) {

		// 1. get user from database
		// 2. if exists - return
		// 3. if not - get from facebook

		String result = null;
		User user = userService.getUserByOAuthToken(accessToken.getToken());
		if (user != null) {
			DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date birthDate = user.getBirthDate();
			if (way == null) {
				result = formRedirectString("ok", user.getId(),
						user.getCountry(), user.getState(), user.getCity(),
						user.getGender(),
						birthDate == null ? null : format.format(birthDate));
			} else {
				result = formJsonString("ok", user.getId(), user.getCountry(),
						user.getState(), user.getCity(), user.getGender(),
						birthDate == null ? null : format.format(birthDate));
			}
		} else {
			Response oauthResponse = getUserProfile(service, accessToken);
			String responseBody = oauthResponse.getBody();
			System.out.println(responseBody);
			boolean hasError = false;
			try {
				JSONObject object = new JSONObject(responseBody);
				if (object != null) {
					JSONObject error = object.getJSONObject("error");
					if (error != null) {
						// doOnError();
						hasError = true;
						System.out.println("ERROR" + error.toString());
						result = formRedirectString("internal_error");
					}
				}
			} catch (JSONException e) {
			}
			if (!hasError) {
				Gson gson = new Gson();
				FbUser facebookAccount = gson.fromJson(responseBody,
						FbUser.class);

				try {
					user = new User();
					user.setName(facebookAccount.getName());
					user.setFirstName(facebookAccount.getFirst_name());
					user.setLastName(facebookAccount.getLast_name());
					user.setGender(facebookAccount.getGender());
					user.setEmail(facebookAccount.getEmail());
					SimpleFbObject location = facebookAccount.getLocation();
					if (location != null) {
						String locationName = facebookAccount.getLocation()
								.getName();
						String[] locationParts = locationName.split(",");
						String city = null;
						String state = null;
						String country = null;
						if (locationParts.length == 2) {
							city = locationParts[0].trim();
							country = locationParts[1].trim();
						}
						user.setCity(city);
						user.setCountry(country);
						user.setState(state);
					}
					String birthday = facebookAccount.getBirthday();
					Date birthDate = null;
					if (!StringUtils.isEmpty(birthday)) {
						birthDate = new SimpleDateFormat("MM/dd/yyyy")
								.parse(birthday);
						System.out.println(birthDate.toString());
						user.setBirthDate(birthDate);
					}
					userService.saveUser(user);
					SocialProfile link = new SocialProfile();
					// link.setUser(user);
					link.setUserId(user.getId());
					link.setAccountId(facebookAccount.getId());
					link.setAccountName(facebookAccount.getUsername());
					link.setAccountLink(facebookAccount.getLink());
					link.setOauthToken(accessToken.getToken());
					List<SocialProfile> links = new ArrayList<SocialProfile>();
					links.add(link);
					// user.setLinks(links);
					// userService.saveUser(user);

					// socialService.saveLinks(links);

					System.out.println("user saved: id = " + user.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				Date bDate = user.getBirthDate();
				DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
				if (way == null) {
					result = formRedirectString("ok", user.getId(),
							user.getCountry(), user.getState(), user.getCity(),
							user.getGender(),
							bDate == null ? null : df.format(bDate));
				} else {
					result = formJsonString("ok", user.getId(),
							user.getCountry(), user.getState(), user.getCity(),
							user.getGender(),
							bDate == null ? null : df.format(bDate));
				}
			}
		}

		System.out.println("result = " + result);
		return result;
	}

	private String formRedirectString(String status) {
		return formRedirectString(status, null, null, null, null, null, null);
	}

	private String formRedirectString(String status, String userId,
			String country, String state, String city, String gender,
			String birthday) {
		StringBuilder builder = new StringBuilder("locawifi://oauth?status=");
		builder.append(status);
		if ("ok".equals(status)) {
			if (!StringUtils.isEmpty(userId)) {
				builder.append("&user=").append(userId);
			}
			builder.append("&social=facebook");
			if (!StringUtils.isEmpty(country)) {
				builder.append("&country=").append(country);
			}
			if (!StringUtils.isEmpty(state)) {
				builder.append("&state=").append(state);
			}
			if (!StringUtils.isEmpty(city)) {
				builder.append("&city=").append(city);
			}
			if (!StringUtils.isEmpty(gender)) {
				builder.append("&sex=").append(
						String.valueOf(gender.charAt(0)).toUpperCase());
			}
			if (!StringUtils.isEmpty(birthday)) {
				builder.append("&birthday=").append(birthday);
			}
		}
		return builder.toString();
	}

	private String formJsonString(String status, String userId, String country,
			String state, String city, String gender, String birthday) {
		JSONObject json = new JSONObject();
		try {
			json.accumulate("status", status);
			if ("ok".equals(status)) {
				if (!StringUtils.isEmpty(userId)) {
					json.accumulate("user", userId);
				}
				json.accumulate("social", "facebook");
				if (!StringUtils.isEmpty(country)) {
					json.accumulate("country", country);
				}
				if (!StringUtils.isEmpty(state)) {
					json.accumulate("state", state);
				}
				if (!StringUtils.isEmpty(city)) {
					json.accumulate("city", city);
				}
				if (!StringUtils.isEmpty(gender)) {
					json.accumulate("sex", String.valueOf(gender.charAt(0))
							.toUpperCase());
				}
				if (!StringUtils.isEmpty(birthday)) {
					json.accumulate("birthday", birthday);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}

	private Response getUserProfile(OAuthService service, Token accessToken) {
		// getting user profile
		OAuthRequest oauthRequest = new OAuthRequest(
				Verb.GET,
				"https://graph.facebook.com/me?fields=id,name,first_name,last_name,username,gender,location,birthday,email,link");
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		System.out.println(oauthResponse.getBody());
		return oauthResponse;
	}
}