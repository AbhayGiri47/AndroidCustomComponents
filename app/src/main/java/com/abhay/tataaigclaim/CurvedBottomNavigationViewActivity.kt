package com.abhay.tataaigclaim

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abhay.tataaigclaim.databinding.ActivityCurvedBottomNavigationViewBinding
import com.abhay.tataaigclaim.swipe_button.OnActiveListener
import com.abhay.tataaigclaim.swipe_button.OnStateChangeListener


class CurvedBottomNavigationViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityCurvedBottomNavigationViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_curved_bottom_navigation_view)

        setupBNV()
        setupSlideButton()
    }

    private fun setupSlideButton() {
        binding.btnSlide.setOnStateChangeListener(object : OnStateChangeListener {
            override fun onStateChange(active: Boolean) {
                Toast.makeText(this@CurvedBottomNavigationViewActivity, "State: $active", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSlide.setOnActiveListener(object : OnActiveListener {
            override fun onActive() {
                Toast.makeText(this@CurvedBottomNavigationViewActivity, "Active!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupBNV() {
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.assigned->{  log("Assigned Clicked")}
                R.id.inProgress->{  log("In Progress Clicked")}
                R.id.upload->{  log("Upload Clicked")}
                R.id.completed->{  log("Completed Clicked")}
            }
            true
        }
    }

    fun log(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}