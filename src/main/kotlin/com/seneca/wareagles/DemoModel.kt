package com.seneca.wareagles

import io.jstach.jstache.JStache

@JStache(path = "demo")
data class DemoModel(val name: String, val visits: Long)
