package com.camunda.fox.showcase.jobannouncement.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.camunda.fox.showcase.jobannouncement.service.UserImageService;
import com.plexiti.helper.Servlets;

@WebServlet(value = UserImageServlet.PATH + "*", loadOnStartup = 1)
public class UserImageServlet extends HttpServlet {
	
	public static final String PATH = "/resources/img/users/";

	private static final long serialVersionUID = -346166704913665327L;

	@Inject
	private UserImageService userImageService;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		response.getOutputStream().write(IOUtils.toByteArray(userImageService.getImage(Servlets.parseFilename(request.getRequestURL().toString(), false))));
	}

}
