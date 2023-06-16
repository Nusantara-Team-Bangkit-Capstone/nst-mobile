package com.capstone.perigigiapps.network.response.messaging

import com.google.gson.annotations.SerializedName

data class MessagingResponse(

	@field:SerializedName("data")
	val data: List<DataItemResponse>
)


