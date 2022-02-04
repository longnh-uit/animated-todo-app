package com.example.todoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton

class AboutFragment : Fragment() {

    private lateinit var btnYoutube: MaterialButton
    private lateinit var btnFacebook: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        btnYoutube = view.findViewById(R.id.btn_youtube)
        btnFacebook = view.findViewById(R.id.btn_facebook)

        // Implement listener
        btnYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC7yZ6keOGsvERMp2HaEbbXQ"))
            startActivity(intent)
        }

        btnFacebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/WhiteVigilante/"))
            startActivity(intent)
        }
    }
}