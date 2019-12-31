package com.devaon.early_buddy_android.data.user

import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import retrofit2.Call

class ServerUserRepository: ServerUserUserRepositoryImpl {
    override fun getUser(id: String): Call<GetUserData> {
        return EarlyBuddyServiceImpl.service.getUser(id)
    }
}