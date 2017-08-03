package com.model2.mvc.service.product;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

//==> 상품관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
///Method TTL : 5EA
public interface ProductDao { 
	
	// insert : 상품등록
	public void addProduct(Product product) throws Exception;
	
	// select one : 상품 1개에 대한 상품정보 확인
	public Product getProduct(int prodNo) throws Exception;

	// select map? list? : 상품 정보 리스트
	public List<Product> getProductList(Search search) throws Exception;
	
	// update : 상품 정보 수정
	public void updateProduct(Product product) throws Exception;
	
	// 게시판 처리
	public int getTotalCount(Search search) throws Exception;

}
