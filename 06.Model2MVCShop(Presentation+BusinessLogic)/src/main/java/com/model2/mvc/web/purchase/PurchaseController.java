package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;



//==> 회원관리 Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		System.out.println("/addPurchaseView.do");
		System.out.println("\n\n\n in addPurchaseView.do prodNo :: "+prodNo);
		Product product = productService.getProduct(prodNo);
		System.out.println("\n\n\n in addPurchaseView.do product :: "+product);
		model.addAttribute("product", product);

		return "forward:/purchase/addPurchaseView.jsp";
	}
	/*
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView() throws Exception {

		System.out.println("/addPurchaseView.do");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	*/

	
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase( @ModelAttribute("purchase") Purchase purchase, 
											@ModelAttribute("product") Product product, 
											@ModelAttribute("user") User user, 
											Model model ) throws Exception {

		System.out.println("/addPurchase.do");
		//Business Logic
		//purchase.setManuDate(purchase.getManuDate().replace("-", "")); //because of DB type or size...
		purchaseService.addPurchase(purchase);
		model.addAttribute("purchase", purchase);
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	/*
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase ) throws Exception {

		System.out.println("/addPurchase.do");
		//Business Logic 후 Model(data) / View(jsp) 정보를 갖는 ModelAndView 생성
		purchase.setManuDate(purchase.getManuDate().replace("-", "")); //because of DB type or size...
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/listPurchase.do");
		
		return modelAndView;
	}
	*/
	
	
	
	@RequestMapping("/getPurchase.do") //data type check
	public String getPurchase( @RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	/*
	@RequestMapping("/getPurchase.do") //data type check
	public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	*/
	
	
	
	@RequestMapping("/updatePurchaseView.do") // admin계정으로 '판매상품관리' 창에서 특정'상품명' 누르면,
	public String updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp"; // 이거 확인
	}
	/*
	@RequestMapping("/updatePurchaseView.do") // admin계정으로 '판매상품관리' 창에서 특정'상품명' 누르면,
	public ModelAndView updatePurchaseView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		
		return modelAndView; // 이거 확인
	}
	*/
	
	
	
	@RequestMapping("/updatePurchase.do") // '수정' button 누르면,
	public String updatePurchase( @ModelAttribute("purchase") Purchase purchase , Model model) throws Exception{

		System.out.println("/updatePurchase.do");
		
		//Business Logic 수행
		purchaseService.updatePurchase(purchase);
		
		return "redirect:/getPurchase.do?tranNo="+purchase.getTranNo()+"&menu=manage"; 
	}
	/*
	@RequestMapping("/updatePurchase.do") // '수정' button 누르면,
	public ModelAndView updatePurchase( @ModelAttribute("purchase") Purchase purchase, @RequestParam("menu") String menu) throws Exception{
	
		System.out.println("/updatePurchase.do");
			
		//Business Logic 수행
		purchaseService.updatePurchase(purchase);
		ModelAndView modelAndView = new ModelAndView();
		
//		modelAndView.setViewName("/getPurchase.do?tranNo="+purchase.getTranNo()+"&menu=manage");
//		@ModelAttribute("purchase") Purchase purchase 를 인자값으로 받으면서....
//		purchase관련 정보가 jsp상에 박히게 된다.
		
		modelAndView.addObject("menu", menu);
		modelAndView.setViewName("/getPurchase.do");
		
		return modelAndView; 
	}
	*/
	

	
	@RequestMapping("/listPurchase.do")
	public String listPurchase( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	/*
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	*/
}