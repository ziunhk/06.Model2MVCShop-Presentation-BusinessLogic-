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



//==> ȸ������ Controller
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
	//setter Method ���� ����
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
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
		//Business Logic �� Model(data) / View(jsp) ������ ���� ModelAndView ����
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
		// Model �� View ����
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	/*
	@RequestMapping("/getPurchase.do") //data type check
	public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	*/
	
	
	
	@RequestMapping("/updatePurchaseView.do") // admin�������� '�ǸŻ�ǰ����' â���� Ư��'��ǰ��' ������,
	public String updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model �� View ����
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp"; // �̰� Ȯ��
	}
	/*
	@RequestMapping("/updatePurchaseView.do") // admin�������� '�ǸŻ�ǰ����' â���� Ư��'��ǰ��' ������,
	public ModelAndView updatePurchaseView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		
		return modelAndView; // �̰� Ȯ��
	}
	*/
	
	
	
	@RequestMapping("/updatePurchase.do") // '����' button ������,
	public String updatePurchase( @ModelAttribute("purchase") Purchase purchase , Model model) throws Exception{

		System.out.println("/updatePurchase.do");
		
		//Business Logic ����
		purchaseService.updatePurchase(purchase);
		
		return "redirect:/getPurchase.do?tranNo="+purchase.getTranNo()+"&menu=manage"; 
	}
	/*
	@RequestMapping("/updatePurchase.do") // '����' button ������,
	public ModelAndView updatePurchase( @ModelAttribute("purchase") Purchase purchase, @RequestParam("menu") String menu) throws Exception{
	
		System.out.println("/updatePurchase.do");
			
		//Business Logic ����
		purchaseService.updatePurchase(purchase);
		ModelAndView modelAndView = new ModelAndView();
		
//		modelAndView.setViewName("/getPurchase.do?tranNo="+purchase.getTranNo()+"&menu=manage");
//		@ModelAttribute("purchase") Purchase purchase �� ���ڰ����� �����鼭....
//		purchase���� ������ jsp�� ������ �ȴ�.
		
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
		
		// Business logic ����
		Map<String , Object> map=purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
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
		
		// Business logic ����
		Map<String , Object> map=purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	*/
}