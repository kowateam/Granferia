package com.app.grantienda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.annotations.rest.PayloadType;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpMethod;
import com.mercadopago.net.MPRestClient;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

@Controller
@RequestMapping("/mp")
public class MercadoPagoController {
	
/*	public String credenciales() {
		
	 JsonObject jsonPayload = new JsonObject();
     jsonPayload.addProperty("grant_type", "client_credentials");
     jsonPayload.addProperty("client_id", MercadoPago.SDK.getClientId());
     jsonPayload.addProperty("client_secret", MercadoPago.SDK.getClientSecret());

     String access_token = null;
     String baseUri = MercadoPago.SDK.getBaseUrl();
     MPApiResponse response = new MPRestClient().executeRequest(
             HttpMethod.POST,
             baseUri + "/oauth/token",
             PayloadType.JSON,
             jsonPayload,
             null);

     System.out.println(response.getStatusCode());

     if (response.getStatusCode() == 200) {
         JsonElement jsonElement = response.getJsonElementResponse();
         if (jsonElement.isJsonObject()) {
             access_token = ((JsonObject)jsonElement).get("access_token").getAsString();
         }
     } else {
         throw new MPException("Can not retrieve the \"access_token\"");
     }
     return access_token;
 }*/

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/premium")
	public String createAndRedirect() throws MPException {
		MercadoPago.SDK.setAccessToken("APP_USR-3753076059821733-120218-376b3fe325e33af60cfecdadc82e78b6-133412580");
		MercadoPago.SDK.setClientId("3753076059821733");
		MercadoPago.SDK.setClientSecret("zy9FmJ1qSBoNntEssFCketoKuQtWnAES");
		Preference preference = new Preference();
		String p= MercadoPago.SDK.getClientId();
		String fg= MercadoPago.SDK.getClientSecret();
		System.out.println("el cliente id "+p);
		System.out.println("el cliente secret "+fg);
		
		preference.setBackUrls(new BackUrls().setFailure("http://granferia.online/mp/failure")
				.setPending("http://granferia.online/mp/pending").setSuccess("http://granferia.online/mp/success"));

		Item item = new Item();
		item.setTitle("Premium").setQuantity(1).setUnitPrice((float) 449.00);
		preference.appendItem(item);
		Preference result = preference.save();
		return "redirect:" + result.getInitPoint();

	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/success")
	public String success(HttpServletRequest request,HttpSession session,@RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String collectionReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, ModelMap modelo) throws MPException {

		Payment collection = Payment.findById(collectionId);
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
	@PreAuthorize("hasAnyRole('ROLE_USER')")
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
