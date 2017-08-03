package com.model2.mvc.web.product;

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
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> ȸ������ Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
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
	
	
	/*
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/addProductView.do")
	public ModelAndView addProductView() throws Exception {

		System.out.println("/addProductView.do");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/addProductView.jsp");
		
		return modelAndView;
	}
	
	
	/*
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		product.setManuDate(product.getManuDate().replace("-", "")); //because of DB type or size...
		productService.addProduct(product);
		
		return "redirect:/listProduct.do";
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic �� Model(data) / View(jsp) ������ ���� ModelAndView ����
		product.setManuDate(product.getManuDate().replace("-", "")); //because of DB type or size...
		productService.addProduct(product);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/listProduct.do");
		
		return modelAndView;
	}
	
	
	/*
	@RequestMapping("/getProduct.do") //data type check
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/getProduct.do") //data type check
	public ModelAndView getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("/product/getProduct.jsp");
		
		return modelAndView;
	}
	
	
	/*
	@RequestMapping("/updateProductView.do") // admin�������� '�ǸŻ�ǰ����' â���� Ư��'��ǰ��' ������,
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		System.out.println("\n\n\n00000in updateProductView.jsp product :: "+product);
		//��������� product�� reg_date�� �� �����µ�....
		
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp"; // �̰� Ȯ��
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/updateProductView.do") // admin�������� '�ǸŻ�ǰ����' â���� Ư��'��ǰ��' ������,
	public ModelAndView updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("/product/updateProductView.jsp");
		
		return modelAndView; // �̰� Ȯ��
	}
	
	
	/*
	@RequestMapping("/updateProduct.do") // '����' button ������,
	public String updateProduct( @ModelAttribute("product") Product product , Model model) throws Exception{

		System.out.println("/updateProduct.do");
		
		//Business Logic ����
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+product.getProdNo()+"&menu=manage"; 
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/updateProduct.do") // '����' button ������,
	public ModelAndView updateProduct( @ModelAttribute("product") Product product, @RequestParam("menu") String menu) throws Exception{
	
		System.out.println("/updateProduct.do");
			
		//Business Logic ����
		productService.updateProduct(product);
		ModelAndView modelAndView = new ModelAndView();
		/*
		modelAndView.setViewName("/getProduct.do?prodNo="+product.getProdNo()+"&menu=manage");
		@ModelAttribute("product") Product product �� ���ڰ����� �����鼭....
		product���� ������ jsp�� ������ �ȴ�.
		*/
		modelAndView.addObject("menu", menu);
		modelAndView.setViewName("/getProduct.do");
		
		return modelAndView; 
	}
	
	
	/*
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	Product�� ModelAndView�� �����*/
	@RequestMapping("/listProduct.do")
	public ModelAndView listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("/product/listProduct.jsp");
		
		return modelAndView;
	}
	
}