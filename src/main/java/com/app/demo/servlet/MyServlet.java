package com.app.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在springboot中添加自己的Servlet有两种方法，
 * 第一种：使用@WebServlet注解，同时在Application.java中使用@ServletComponentScan注解，可以自动注册
 * 第二种：不使用注解，但是需要将自定义的Servlet作为一个bean注入到启动类中
 * @author yuzhiyou
 *
 */
@WebServlet(urlPatterns="/myServlet/*")
public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = -698540820152218069L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		System.out.println("===============MyServlet.doGet()==============");
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("==============MyServlet.doPost()=============");
		resp.setContentType("text/html"); 
		resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter(); 

        out.println("<html>"); 

        out.println("<head>"); 

        out.println("<title>Hello World</title>"); 

        out.println("</head>"); 

        out.println("<body>"); 

        out.println("<h1>这是：myServlet</h1>"); 

        out.println("</body>"); 

        out.println("</html>"); 
	}

	
}
