package com.digihealth.anesthesia.common.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digihealth.anesthesia.basedata.po.BasRoutingAccessControl;
import com.digihealth.anesthesia.common.listener.ConstantHolder;
import com.digihealth.anesthesia.common.listener.PathAccessThread;
import com.digihealth.anesthesia.common.utils.CacheUtils;


public class PathHandleFilter implements Filter{
	private Logger logger = Logger.getLogger(PathHandleFilter.class);
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestUrI = req.getRequestURI();
		String ctxPath = req.getContextPath();
		String path = requestUrI.substring(ctxPath.length());
		logger.info("PathHandleFilter:"+"doFilter---"+ path);
		
		String beid = req.getHeader("beid");
		if(null== beid || StringUtils.isBlank(beid)){
			beid = (String)CacheUtils.get(ConstantHolder.ROUTING_ACCESS_CACHE, ConstantHolder.CUR_BEID);
		}
		String key = PathAccessThread.getKey(beid, path);
		Object obj =  CacheUtils.get(ConstantHolder.ROUTING_ACCESS_CACHE,key);
		
		if(null != obj){
			BasRoutingAccessControl rac = (BasRoutingAccessControl) obj;
			if (rac != null) {
				String clazzUri = rac.getClazzUri();
				String method = rac.getMethod();
				// /sys/login 截取 /sys/
				int lastIndexOf = path.lastIndexOf("/");
				int indexOf = path.indexOf("/", 0);
				String substr = path.substring(indexOf, lastIndexOf + 1);
				String dispatcherPath = substr + method;
				logger.info("PathHandleFilter:" + "clazz==" + clazzUri + ",method===" + method+",substr="+substr+",dispatcherPath="+dispatcherPath);
				req.getRequestDispatcher(dispatcherPath).forward(request, response);
			}
		}else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
