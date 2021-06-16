package com.trip.my.room.server.user.controller

import com.trip.my.room.server.config.MyKakaoConfigurationProperties
import com.trip.my.room.server.config.MyNaverConfigurationProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginTestController(val myKakaoConfig: MyKakaoConfigurationProperties,
						  val myNaverConfig: MyNaverConfigurationProperties) {
	
	@GetMapping("/")
	fun loginPage(model: Model): String {
		println(myKakaoConfig)
		model.addAttribute("client_id", myKakaoConfig.clientId)
		model.addAttribute("redirectUri", myKakaoConfig.redirectUrl)
//		model.addAttribute("nclient_id", myNaverConfig.clientId)
//		model.addAttribute("nredirectUri", myNaverConfig.redirectUrl)
		return "login"
	}
}
