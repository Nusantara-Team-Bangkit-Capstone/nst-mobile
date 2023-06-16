package com.capstone.perigigiapps.ui.screen.konsultasi

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import com.capstone.perigigiapps.data.entity.Chat
import com.capstone.perigigiapps.databinding.ActivityMessagingBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult

class MessagingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagingBinding
    private lateinit var messagingViewModel: MessagingViewModel
    private lateinit var messagingAdapter: MessagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messagingAdapter = MessagingAdapter()
        binding.messageRecyclerView.adapter = messagingAdapter
        val messagingRepository = Injection.provideMessagingRepository()
        val factory = MessagingViewModel.MessagingViewModelFactory(messagingRepository)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()
        val jwt = JWT(token)
        val idSender = jwt.getClaim("id").asString()
        val nameSender = jwt.getClaim("name").asString()
        val nameReceivers = intent.getStringExtra(
            nameReceiver
        ).orEmpty()
        val idReceivers = intent.getStringExtra(
            idReceiver
        ).orEmpty()
        val actionBar = supportActionBar
        actionBar?.title = nameReceivers
        messagingViewModel = ViewModelProvider(this, factory)[MessagingViewModel::class.java]

        binding.sendButton.setOnClickListener {
            var chatText = binding.messageEditText.text.toString()
            if (chatText == "") return@setOnClickListener
            chatText = chatText.trim()
            val chat = Chat(
                sender = idSender.toString(),
                receiver = idReceivers,
                message = chatText,
                receiverName = nameReceivers,
                senderName = nameSender.toString()
            )
            messagingViewModel.postChat(token = token, chat = chat)
                .observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is NetworkResult.Loading -> {
                                binding.progressBar.isVisible = true
                            }

                            is NetworkResult.Success -> {
                                binding.progressBar.isVisible = false
                                val response = result.data

                                binding.messageRecyclerView.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                messagingAdapter.submitList(response)
                                binding.messageEditText.setText(null)
                            }

                            is NetworkResult.Error -> {
                                binding.progressBar.isVisible = false
                                Toast.makeText(this, result.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
        }
    }

    companion object {
        val idReceiver = "idReceiver"
        val nameReceiver = "nameReceiver"
    }
}