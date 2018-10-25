package com.github.wkw.share.vo

import com.google.gson.JsonObject

data class PushEvent(val type: Int, val data: JsonObject)