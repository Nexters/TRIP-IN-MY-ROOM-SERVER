package com.trip.my.room.server.common.filter

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class CorsFilter : Filter {
	@Throws(ServletException::class)
	override fun init(filterConfig: FilterConfig?) {
	}
	
	@Throws(IOException::class, ServletException::class)
	override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse, filterChain: FilterChain) {
		val response = servletResponse as HttpServletResponse
		response.setHeader("Access-Control-Allow-Origin", "*")
		response.setHeader("Access-Control-Allow-Methods", "*")
		response.setHeader("Access-Control-Allow-Headers", "*")
		response.setHeader("Access-Control-Allow-Credentials", "false")
		response.setHeader("Access-Control-Max-Age", "1728000")
		filterChain.doFilter(servletRequest, response)
	}
	
	override fun destroy() {}
}

