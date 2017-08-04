package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

//==> 구매관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
///Method TTL : 6EA
public interface PurchaseDao {

	// item 구매 추가
	public void addPurchase(Purchase purchase) throws Exception; 
	
	// 구매한 item 찾기 by tranNo
	public Purchase getPurchase(int tranNo) throws Exception;
	
	// 구매한 item 찾기 by prodNo
	public Purchase getPurchase2(int prodNo) throws Exception;
	
	// 구매한 item 관련 정보 수정
	public void updatePurchase(Purchase purchase) throws Exception;
	
	// 구매완료 -> 배송중 -> 배송완료 수정
	public void updateTranCode(Purchase purchase) throws Exception;

	// 구매 item 목록 
	public List<Purchase> getPurchaseList(Search search) throws Exception;
	
	// 게시판 처리
	public int getTotalCount(Search search) throws Exception;
	
	// 이건뭐야 : 혹시모르니...
	public Map<String, Object> getSaleList(Search search) throws Exception;
		
}
