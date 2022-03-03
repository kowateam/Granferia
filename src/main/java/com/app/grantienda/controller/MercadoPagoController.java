package com.app.grantienda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.grantienda.service.EmprendimientoService;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

@Controller
@RequestMapping("/mp")
public class MercadoPagoController {
	
	@Autowired
	private EmprendimientoService es;
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/premium")
	public String createAndRedirect() throws MPException {
		Preference preference = new Preference();

		preference.setBackUrls(new BackUrls().setFailure("http://localhost:8080/mp/failure")
				.setPending("http://localhost:8080/mp/pending").setSuccess("http://localhost:8080/mp/success"));

		Item item = new Item();
		item.setTitle("Principiante").setQuantity(1).setUnitPrice((float) 399.00);
		preference.appendItem(item);
		Preference result = preference.save();
		return "redirect:" + result.getSandboxInitPoint();

	}


	@GetMapping("/success")
	public String success(HttpServletRequest request,HttpSession session,@RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String collectionReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, ModelMap modelo) throws MPException {

		Payment collection = com.mercadopago.resources.Payment.findById(collectionId);
		System.out.println(collection.toString());
		System.out.println(collectionId);
		System.out.println(collectionStatus);
		System.out.println(collectionReference);
		System.out.println(siteId);
		System.out.println(paymentType);
		System.out.println(preferenceId);
		System.out.println(processingMode);
		System.out.println(merchantAccountId);
		modelo.addAttribute("status", collection);
		
		session.setAttribute("statuspago", collectionStatus);
		
		return "redirect:/emp/crearemp";
	}

	@GetMapping("/failure")
	public String failure(HttpServletRequest request,HttpSession session, @RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String collectionReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, ModelMap modelo) throws MPException {

		Payment collection = com.mercadopago.resources.Payment.findById(collectionId);
		System.out.println(collection.toString());
		System.out.println(collectionId);
		System.out.println(collectionStatus);
		System.out.println(collectionReference);
		System.out.println(siteId);
		System.out.println(paymentType);
		System.out.println(preferenceId);
		System.out.println(processingMode);
		System.out.println(merchantAccountId);
		modelo.addAttribute("payment", collection);
		modelo.addAttribute("errorpago", "errorpago");
		
		session.setAttribute("statuspago", collectionStatus);
		return "registroEstado.html";
	}
}
