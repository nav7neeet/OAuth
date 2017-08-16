package com.oauth.google;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
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
	private static final String resourceUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		System.out.println("=== in the redirect page ===");
		String code = request.getParameter("code");
		System.out.println("code - " + code+"\n");

		// Check if the user has given authorization. Code parameter will not be null.
		if (code != null)
		{
			HttpSession session = request.getSession();
			OAuth20Service authUrl=(OAuth20Service) session.getAttribute("authUrl");
			
			try
			{
				// Get the access token by sending code in the request
				OAuth2AccessToken accessToken = authUrl.getAccessToken(code);
				System.out.println("accessToken - " + accessToken+"\n");
	
				// Sign the request using access token
				final OAuthRequest authRequest = new OAuthRequest(Verb.GET, resourceUrl);
				authUrl.signRequest(accessToken, authRequest);
				
				//Get user information
				final Response userInformation = authUrl.execute(authRequest);
				System.out.println("response code - "+userInformation.getCode());
				System.out.println("response body \n"+userInformation.getBody());
				
				request.setAttribute("userInformation", userInformation.getBody());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			request.setAttribute("userInformation", "{}");
		}
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
	}
}
