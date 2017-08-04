package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	///Field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;
	
	///Constructor
		public PurchaseServiceImpl(){
			System.out.println(this.getClass());
		}
	
	public void setPurchaseDao(PurchaseDao purchaseDao){
		System.out.println();
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.getPurchase(tranNo);
	}

	public Purchase getPurchase2(int prodNo) throws Exception {
		return purchaseDao.getPurchase2(prodNo);
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

	public Map<String, Object> getPurchaseList(Search search) throws Exception {
		
		List<Purchase> list = purchaseDao.getPurchaseList(search); // purchase 객체를 element로 받는 list 생성
		int totalCount = purchaseDao.getTotalCount(search); // totalCount는 getTotalCount method를 이용하여 return 된 값을 대입
		
		Map<String, Object> map = new HashMap<String, Object>(); //HashMap
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	//....필요한가요?
	public Map<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
