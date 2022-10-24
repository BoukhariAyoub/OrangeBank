package com.boukhari.orangebank.data.remote.model

import com.google.gson.annotations.SerializedName

data class LicenseResponse(
    @SerializedName("key") var key: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("spdx_id") var spdx_id: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("node_id") var node_id: String? = null,
)
