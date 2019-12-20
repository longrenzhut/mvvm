package com.zhongcai.common.widget.optaddress


data class AddressModel(
    var province: String = "",
    var provinceId: String = "-1",
    var city: String = "",
    var cityId: String = "-1",
    var area: String = "",
    var areaId: String = "-1",
    var areas: String = "",
    var areaIds: String = "",
    var address: String = ""
) {
    fun getAddr2(): String = province + city

    fun getAddr(): String = province + city + area

    fun getAddrs(): String = province + city + areas

    fun getAddressIds(): String = "$provinceId,$cityId,$areaId"
}
