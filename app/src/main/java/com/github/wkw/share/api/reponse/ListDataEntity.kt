package com.github.wkw.share.api.reponse

import java.util.*

class ListDataEntity<T>(val hasMore: Boolean = false, val total: Int = 0, val list: List<T> = Collections.emptyList())