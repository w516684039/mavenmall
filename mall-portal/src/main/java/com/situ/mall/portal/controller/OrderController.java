package com.situ.mall.portal.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.xb.xmlconfig.NamespaceList.Member2.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.Order;
import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.Shipping;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IOderItemService;
import com.situ.mall.core.service.IOrderService;
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
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOderItemService orderItemService;


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
	
	@RequestMapping("/addOrder")
	@ResponseBody
	public ServerResponse addOrder(Integer shippingId,HttpSession session,BigDecimal totalprice,
			Integer paytype,Integer postage,HttpServletRequest request,HttpServletResponse response){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		Order order = new Order();
		//设置当前时间为订单编号
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(time);
		order.setOrderNo(Long.parseLong(format.format(date)));
		order.setUserId(user.getId());
		order.setShippingId(shippingId);
		order.setPayment(totalprice);
		order.setPaymentType(paytype);
		order.setPostage(postage);
		order.setSendTime(new Date());
		order.setCreateTime(new Date());
		order.setEndTime(new Date());
		//订单放入数据库
		orderService.add(order);
		//从cookie得到购物车cartVo
		CartVo cartVo = getCartVoFromCookie(request);
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for(CartItemVo cartItemVo:cartItemVos){
			if(cartItemVo.getIsChecked() == Const.CartChecked.CHECKED){
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo(order.getOrderNo());
				orderItem.setUserId(user.getId());
				Product product = productService.selectById(cartItemVo.getProduct().getId());
				orderItem.setProductId(product.getId());
				orderItem.setProductName(product.getName());
				orderItem.setProductImage(product.getMainImage());
				orderItem.setCurrentUnitPrice(product.getPrice());
				orderItem.setQuantity(cartItemVo.getAmount());
				orderItem.setTotalPrice(totalprice);
				orderItem.setCreateTime(new Date());
				orderItem.setUpdateTime(new Date());
				orderItemService.addOrderItem(orderItem);
			}
		}
		//遍历cartVo把isChecked=1的删除,然后存到cookie
		Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo cartItemVo = iterator.next();
			if (cartItemVo.getIsChecked() == Const.CartChecked.CHECKED) {
				iterator.remove();
			}
		}
		Common.setCartVoToCookie(response, cartVo);
		return ServerResponse.createSuccess("订单成功");
	}
	@RequestMapping("/getpay")
	public String getpay(HttpSession session,Model model){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		model.addAttribute("user",user);
		List<Order> list = orderService.selectByUserId(user.getId());
		model.addAttribute("list", list);
		return "pay";
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
