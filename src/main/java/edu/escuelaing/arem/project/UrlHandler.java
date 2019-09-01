package edu.escuelaing.arem.project;

import java.lang.reflect.Method;

public class UrlHandler implements Handler{
	
	private Method method;

	public UrlHandler(Method method) {
		this.method = method;
	}

	public String process() {
		String error;
		try {
			return (String) method.invoke(null, null);
		} catch (Exception e) {
			error = e.toString();
			e.printStackTrace();
			return error;
		}
	}
	
}
