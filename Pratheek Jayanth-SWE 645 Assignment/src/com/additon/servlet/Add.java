package com.additon.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Add
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Add" })
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        
    }

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int i = Integer.parseInt(req.getParameter("t1"));
		int j = Integer.parseInt(req.getParameter("t2"));
		int k = i+j;
		PrintWriter out = res.getWriter();
		out.println(k);
	}

}
