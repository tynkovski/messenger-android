package com.tynkovski.apps.messenger.core.data.util

import java.time.format.DateTimeFormatter

private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
internal val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
