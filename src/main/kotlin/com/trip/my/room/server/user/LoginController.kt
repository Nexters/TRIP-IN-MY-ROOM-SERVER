package com.trip.my.room.server.user

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
	
	@GetMapping("")
	fun loginPage(model: Model): String {
		return "login"
	}
}