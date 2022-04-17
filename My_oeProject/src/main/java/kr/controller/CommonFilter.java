package kr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;

public class CommonFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//카테고리드롭다운 
		OCategoryDAO dao2 = OCategoryDAO.getInstance();
		List<OCategoryVO> list = null;
		try {
			list = dao2.getListCateMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("cateName_list", list);

		chain.doFilter(request, response);
		
	}

}
