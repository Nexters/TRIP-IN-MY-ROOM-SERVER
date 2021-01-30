package com.trip.my.room.server.user

import com.trip.my.room.server.config.MyConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController(@Autowired val myConfig: MyConfigurationProperties) {
	
	@GetMapping("")
	fun loginPage(model: Model): String {
		model.addAttribute("client_id", myConfig.clientId)
		model.addAttribute("redirectUri", myConfig.redirectUrl)
		return "login"
	}
}