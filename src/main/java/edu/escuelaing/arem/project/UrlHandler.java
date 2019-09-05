package edu.escuelaing.arem.project;

import java.lang.reflect.Method;

public class UrlHandler implements Handler{
	
	private Method method;

	public UrlHandler(Method method) {
		this.method = method;
	}

	public String process(String[] params) {
		String error;
		try {
			return (String) method.invoke(null,params);
		} catch (Exception e) {
			error = e.toString();
			e.printStackTrace();
			return error;
		}
	}
	
}
