package com.example.lostnexus.kotlin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lostnexus.models.Message
import com.example.lostnexus.models.UserProfile
import com.example.lostnexus.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

 class ChatViewModel(): ViewModel() {
    var chatRepository: ChatRepository = ChatRepository()
   private var chatuserLivedata: MutableLiveData<MutableList<UserProfile>> = MutableLiveData()

  open var messageListLiveData:MutableLiveData<MutableList<Message>>?= MutableLiveData()

    fun loadMessages(id:String){
         messageListLiveData  = chatRepository.loadMessages(id);
    }

    fun getchatUsers(): MutableLiveData<MutableList<UserProfile>> {
        Log.i("i" ,"calle model")
        chatuserLivedata=  chatRepository.getchatUserLiveData()
        return chatuserLivedata
    }

    fun sendMessage(text:String ,senderid:String , recieverid:String){
         chatRepository.sendMessage(text , senderid , recieverid);
    }
    fun getmessageListLiveData(){

    }






}