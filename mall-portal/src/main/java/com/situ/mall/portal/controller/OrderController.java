package com.situ.mall.portal.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.Shipping;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IProductService;
import com.situ.mall.core.service.IShippingService;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;


@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IShippingService shippingService;
	@Autowired
	private IProductService productService;


	@RequestMapping("/getOrderPage")
	public String getOrderPage(HttpSession session,Model model,HttpServletRequest request){
		//从session中获取user
		User user = (User)session.getAttribute(Const.CURRENT_USER);
		//2.通过user得到收获地址
		Shipping shippings = shippingService.findByUserId(user.getId());
		model.addAttribute("shipping", shippings);
		model.addAttribute("user", user);
		//3.将Cookie里面的购物车信息转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		//将CartItemVo里面的Product填满信息，因为现在只有一个id
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for(CartItemVo cartItemVo: cartItemVos){
			Product product = productService.selectById(cartItemVo.getProduct().getId());
			cartItemVo.setProduct(product);
		}
		model.addAttribute("cartVo", cartVo);
		return "order";
		
	}

	private String CART_COOKIE = "cart_cookie";
	//将Cookie中的购物车信息转换为CartVo对象
	private CartVo getCartVoFromCookie(HttpServletRequest request) {
		CartVo cartVo = null;
		ObjectMapper objectMapper = new ObjectMapper();
		// 只有对象中不为null才转换
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		Cookie[] cookies = request.getCookies();
		
		if (null != cookies&&cookies.length!=0) {
			for(Cookie cookie:cookies){
				if (CART_COOKIE.equals(cookie.getName())) {
					String value = cookie.getValue();
					try {
						cartVo = objectMapper.readValue(value, CartVo.class);
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return cartVo;
	}

}
