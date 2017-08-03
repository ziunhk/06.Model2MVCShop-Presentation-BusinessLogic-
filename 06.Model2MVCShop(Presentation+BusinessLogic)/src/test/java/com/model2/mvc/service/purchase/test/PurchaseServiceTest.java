package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;


/*
 *	FileName :  PurchaseServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
															"classpath:config/context-aspect.xml",
															"classpath:config/context-mybatis.xml",
															"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void testAddPurchase() throws Exception {

		Purchase purchase = new Purchase();

		// product number의 경우, 바로 purchase 객체에 입력이 불가하여 product 객체를 생성하고 입력해준후에 그 (product) 객체를 가져온다.
		Product product = new Product(); 
		product.setProdNo(10005);
		purchase.setPurchaseProd(product);

		User buyer = new User();
		buyer.setUserId("user08");
		purchase.setBuyer(buyer);

		purchase.setPaymentOption("2  ");
		purchase.setReceiverName("testRecvName");
		purchase.setReceiverPhone("11111");
		purchase.setDivyAddr("testAddr");
		purchase.setDivyRequest("testDlvyReq");
		purchase.setTranCode("1");
		purchase.setDivyDate("20170811");

		purchaseService.addPurchase(purchase);

//		purchase = purchaseService.getPurchase(10056); // DB에 저장되는 tran_no 확인
//
//		// ==> console 확인
//		System.out.println(purchase);
//
//		// ==> API 확인
//		// DB에 저장되는 transaction table의 prod_no 확인
//		Assert.assertEquals(10000, purchase.getPurchaseProd().getProdNo()); 
//		Assert.assertEquals("user08", purchase.getBuyer().getUserId());
//
//		Assert.assertEquals("2  ", purchase.getPaymentOption());
//		Assert.assertEquals("testRecvName", purchase.getReceiverName());
//		Assert.assertEquals("11111", purchase.getReceiverPhone());
//		Assert.assertEquals("testAddr", purchase.getDivyAddr());
//		Assert.assertEquals("testDlvyReq", purchase.getDivyRequest());
//		Assert.assertEquals("20170811", purchase.getDivyDate());
	}

	//@Test
	public void testGetPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase(10080); // tranNo로 purchase 객체 불러오기

		// ==> console 확인
		System.out.println("13579inTestGetPurchase() :: "+purchase);

		// ==> API 확인
		Assert.assertEquals(10005, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user08", purchase.getBuyer().getUserId());
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("testRecvName", purchase.getReceiverName());
		Assert.assertEquals("11111", purchase.getReceiverPhone());
		Assert.assertEquals("testAddr", purchase.getDivyAddr());
		Assert.assertEquals("testDlvyReq", purchase.getDivyRequest());
		Assert.assertEquals("1  ", purchase.getTranCode());
		//Assert.assertEquals("17/08/11", purchase.getDivyDate());

	}

	//@Test
	public void testUpdatePurchase() throws Exception {

		// Purchase purchase = purchaseService.getPurchase();
		// parameter로 1. tranNo를 받을지 혹은 2. prodNo를 받을지 먼저 생각
		// 1.의 경우 먼저 tranNo를 알아야 한다.(tranNo 의 경우는 sequence number로 자동으로 입력되기 때문에 DB상에서 직접 찾아서...)
		// tranNo 입력된 거 DB에서 확인하여 직접 입력예정
		
		Purchase purchase = null;
		purchase = purchaseService.getPurchase(10080);
		System.out.println("\n24inPurhaseServiceTest.java :: "+purchase);
		
		purchase.setPaymentOption("2");
		purchase.setReceiverName("chngRcvNm");
		purchase.setReceiverPhone("22222");
		purchase.setDivyAddr("chngAddr");
		purchase.setDivyRequest("chngRqst");
		purchase.setDivyDate("20170822");
		
		System.out.println("\n2468inPurhaseServiceTest.java :: "+purchase);

		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10080);
		Assert.assertNotNull(purchase);

		// ==> console 확인
		System.out.println(purchase);

		// ==> API 확인 parameter ( expected, actual )
		// 수정후에 입력된 값과 비교
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("chngRcvNm", purchase.getReceiverName());
		Assert.assertEquals("22222", purchase.getReceiverPhone());
		Assert.assertEquals("chngAddr", purchase.getDivyAddr());
		Assert.assertEquals("chngRqst", purchase.getDivyRequest());
		Assert.assertEquals("2017-08-22", purchase.getDivyDate().split(" ")[0]); //split(" ")[0] 대신 substring(0, 10)) 사용가능
	}

	//@Test
	public void testGetPurchaseListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = purchaseService.getPurchaseList(search);

		list = (List<Object>) map.get("list");
		System.out.println("list :: " + list);
		System.out.println("list.size() :: " + list.size());
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	// @Test
	public void testGetPurchaseListByProdNo() throws Exception { // 이름은 상관없음

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("admin");
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("0");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = purchaseService.getPurchaseList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	// @Test
	public void testGetPurchaseListByProductName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("SCOTT");
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = purchaseService.getPurchaseList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
}