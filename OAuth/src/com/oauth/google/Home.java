package com.oauth.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final String cliendId = "984169855535-rmsfbv11ikina3hrb3n85h0nt93hdb2t.apps.googleusercontent.com";
		final String clientSecret = "ZZ70ScMw5xikMTozcTLUxMM6";
		final String redirectUri = "http://127.0.0.1:8080/OAuth/redirect";
		final String scope = "https://www.googleapis.com/auth/plus.login";
		final String secretState = "secret" + new Random().nextInt(999_999);

		//Prepare authorization url
		OAuth20Service authUrl = new ServiceBuilder().apiKey(cliendId).apiSecret(clientSecret).scope(scope)
				.state(secretState).callback(redirectUri).build(GoogleApi20.instance());

		System.out.println("=== Test OAuth ===");
		System.out.println();

		// Adding additional params
		final Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("access_type", "offline");
		additionalParams.put("prompt", "consent");

		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = authUrl.getAuthorizationUrl(additionalParams);

		System.out.println(authorizationUrl + "\n");

		HttpSession session = request.getSession();
		session.setAttribute("authUrl", authUrl);
		
		String hardCodedValue = "https://accounts.google.com/o/oauth2/auth?access_type=offline&prompt=consent&response_type=code&client_id=984169855535-rmsfbv11ikina3hrb3n85h0nt93hdb2t.apps.googleusercontent.com&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2FOAuth%2FOAuthRedirect&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login&state=secret898652";
		response.sendRedirect(authorizationUrl);
//		response.sendRedirect(authorizationUrl);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
