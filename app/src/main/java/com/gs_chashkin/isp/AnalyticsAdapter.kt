package com.gs_chashkin.isp

import javax.inject.Inject


//constructor injection
class AnalyticsAdapter @Inject constructor(
    private val service: AnalyticsService
) {
    // class stuff

}