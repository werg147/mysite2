package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	
	//필드
	//생성자-디폴트
	//메소드gs
	
	//메소드일반
	//포워드
	public static void forward(HttpServletRequest request,
						HttpServletResponse response,
						String path) throws ServletException, IOException {
		
		//jsp에 포워드
		RequestDispatcher rd = request.getRequestDispatcher(path); //jsp파일 위치
		rd.forward(request, response);
		
	}
	
	//리다이렉트
	public static void redirect(HttpServletRequest request,
								HttpServletResponse response,
								String url) throws IOException {
		response.sendRedirect(url);
	}
	

}
