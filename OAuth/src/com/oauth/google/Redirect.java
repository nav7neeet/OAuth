package com.oauth.google;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

@WebServlet(urlPatterns = { "/redirect" }, asyncSupported = true)
public class Redirect extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String protectedResourceUrl = "https://www.googleapis.com/plus/v1/people/me";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		System.out.println("=== in the call back servlet ===");

		String code = request.getParameter("code");

		System.out.println("code - " + code+"\n");

		HttpSession session = request.getSession();
				
		OAuth20Service authUrl=(OAuth20Service) session.getAttribute("authUrl");

		//try puttign it inside try block
		OAuth2AccessToken accessToken = null;

		try
		{
			accessToken = authUrl.getAccessToken(code);

			System.out.println("accessToken - " + accessToken+"\n");

			final OAuthRequest authRequest = new OAuthRequest(Verb.GET,
					protectedResourceUrl);
			authUrl.signRequest(accessToken, authRequest);

			final Response response2 = authUrl.execute(authRequest);

			System.out.println();
			System.out.println("response code - "+response2.getCode());
			System.out.println("response body \n"+response2.getBody());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		System.out.println("post call back");
	}

}
