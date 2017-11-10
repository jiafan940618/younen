package com.yn.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yn.model.User;
import com.yn.session.SessionCache;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

	   protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		
		logger.info("进入监听器session 记录  初始化---- --- ---->");
		
		HttpSession session =	event.getSession();
		
		ServletContext application = session.getServletContext();
		
		User user = SessionCache.instance().getUser(); 
		
		List<User> list = (List<User>)application.getAttribute("UserList");
		
		if(list == null ){
			
			List<User> newlist = new ArrayList<User>();
			
			application.setAttribute("UserList", newlist);
		}
		
		list.add(user);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		logger.info("进入监听器session 销毁---- --- ---->");
		
		HttpSession session = event.getSession();
		
		ServletContext application =session.getServletContext();
		
		User user = SessionCache.instance().getUser(); 
		
		List<User> list = (List<User>)application.getAttribute("UserList");
		
		list.remove(user);
	
	}
	
	
	
	
	
	

}

