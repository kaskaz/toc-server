package com.theoriginalcover.server

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.theoriginalcover.server")
		.start()
}
