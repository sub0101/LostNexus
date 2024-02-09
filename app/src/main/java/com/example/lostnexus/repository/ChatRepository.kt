package com.example.lostnexus.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lostnexus.models.Message
import com.example.lostnexus.models.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class ChatRepository {
    val instance: FirebaseDatabase = FirebaseDatabase.getInstance()
    var chatuserLiveData: MutableLiveData<MutableList<UserProfile>> = MutableLiveData()
    var messageListLiveData:MutableLiveData<MutableList<Message>>?= MutableLiveData()

    init
    {


    }

    fun getchatUserLiveData(): MutableLiveData<MutableList<UserProfile>> {
        val mp1 = mutableMapOf<String, Boolean>()

        Log.i("repo", "repo called")
        instance.getReference("ChatUsers").child(FirebaseAuth.getInstance().uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
println(snap)
                        val str:String?= snap.getValue(String::class.java)
                        mp1.put(str!!, true)

                    }
                    getUserProfile(mp1)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        Log.i("repo", "repo called gain")

        return chatuserLiveData;
    }

    fun getUserProfile(map: MutableMap<String, Boolean>) {

        var userProfile: MutableList<UserProfile>? = mutableListOf();
println("profile list")
        instance.getReference("UserProfile").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {

                    val pofile: UserProfile? = snap.getValue(UserProfile::class.java)
                    if (map[pofile?.getUuid()] == true) {
                        println(pofile?.getUuid())

                        if (pofile != null) {
                            userProfile?.add(pofile)
                        }
                    }
//                    val profile:UserProfile ?= snap.getValue(UserProfile::class.java)
//                    if (profile != null) {
//                        if(map[snap.] == true) userProfile.add(profile)

//                    }
                }
                chatuserLiveData.value = userProfile
                println(chatuserLiveData.value)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


//                chatuserLiveData?.value  = userProfile;


        })


    }

    fun sendMessage(text:String ,senderid:String , recieverid:String){
        val senderRoom:String=senderid+recieverid
        val recieverRoom:String=recieverid+senderid
        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy '\n' HH:mm:ss ");
        val  date:String = dateFormat.format(Date());
        val randomKey:String = FirebaseDatabase.getInstance().getReference().push().key.toString();
        val message =  Message(UUID.randomUUID().toString() ,text , FirebaseAuth.getInstance().uid , date);
        FirebaseDatabase.getInstance().getReference("Chatting").child(senderRoom).child(randomKey).setValue(message).addOnSuccessListener{unused:Void?->}
        FirebaseDatabase.getInstance().getReference("Chatting").child(recieverRoom).child(randomKey).setValue(message).addOnSuccessListener{unused:Void? -> {}};

    }
fun loadMessages(id:String):MutableLiveData<MutableList<Message>>{
    val senderRoom:String= FirebaseAuth.getInstance().uid +id;

    println("called repository")
    FirebaseDatabase.getInstance().getReference("Chatting").child(senderRoom).addValueEventListener(object:ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val mlist = mutableListOf<Message>();
            println("getting data")
           for(snap in snapshot.children){
             val   m = snap.getValue(Message::class.java);
            mlist.add(m!!);
               println(m.message)
           }
            messageListLiveData?.value = mlist
            println(messageListLiveData?.value)
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
return messageListLiveData!!;
}

    fun getMessages(){

//        FirebaseDatabase.getInstance().getReference("Chatting").child(senderRoom)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    messageArrayList.clear()
//                    for (snapshot1 in snapshot.children) {
//                        messageArrayList.add(snapshot1.getValue<Message>(Message::class.java))
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {}
//            })
    }

}