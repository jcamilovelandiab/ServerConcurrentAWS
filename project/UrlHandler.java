package edu.escuelaing.arem.project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.escuelaing.arem.project.notation.Web;

public class UrlHandler implements Handler{
	
	private Method method;

	public UrlHandler(Method method) {
		this.method = method;
	}

	@Override
	public String process(){
		String error;
		try {
			return (String) method.invoke(null, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			error = e.toString();
			e.printStackTrace();
			return error;
		}
	}
	
}
