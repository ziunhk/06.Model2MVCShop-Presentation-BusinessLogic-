package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
															"classpath:config/context-aspect.xml",
															"classpath:config/context-mybatis.xml",
															"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProductName");
		product.setProdDetail("testProductDetail");
		product.setManuDate("20111111");
		product.setFileName("testImageFile");
		product.setPrice(11111);
		
		productService.addProduct(product);
		
		// DB에 저장되는 prod_no를 먼저 확인한 후에 테스트 실행할 것
		product = productService.getProduct(10080); 

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(10080, product.getProdNo()); // DB에 저장되는 prod_no 확인
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20111111", product.getManuDate());
		Assert.assertEquals("testImageFile", product.getFileName());
		Assert.assertEquals(11111, product.getPrice());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();

		product = productService.getProduct(10080);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(10080, product.getProdNo());
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20111111", product.getManuDate());
		Assert.assertEquals(11111, product.getPrice());

	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		
		Product product = productService.getProduct(10080);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20111111", product.getManuDate());
		Assert.assertEquals(11111, product.getPrice());

		product.setProdName("change");
		product.setProdDetail("DetailChanged");
		product.setManuDate("20000222");
		product.setPrice(22222);
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10080);
		
		Assert.assertNotNull(product); // product 객체가 null이 아님을 확인한다.
		
		//==> console 확인
		System.out.println("inProductServiceTest product :: "+product);
			
		//==> API 확인 parameter ( expected, actual )
		Assert.assertEquals("change", product.getProdName()); 
		Assert.assertEquals("DetailChanged", product.getProdDetail());
		Assert.assertEquals("20000222", product.getManuDate());
		Assert.assertEquals(22222, product.getPrice());
	 }
	 
	
	 //==>  주석을 풀고 실행하면....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	System.out.println("inProductServiceTest list :: "+list);
	 	System.out.println("inProductServiceTest list.size() :: "+list.size());
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{ //이름은 상관없음
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("admin");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("SCOTT");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}