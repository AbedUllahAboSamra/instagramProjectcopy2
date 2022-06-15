package com.example.instagramproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagramproject.R
import com.example.instagramproject.ScandryFragments.*
import com.example.instagramproject.databinding.FragmentMainBinding
import com.example.instagramproject.screen.MainActivity
import com.google.android.material.tabs.TabLayout


class MainFragment : Fragment() {

    // initialization   :: :: ::
    lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.con_FragmentContainer, SubMainFragment())
            .commit()
    }
    // initialization   :: :: ::
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //sub tab layout
        binding.tabTapLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, SubMainFragment())
                            .commit()
                    }
                    1 -> {

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, SearchFragment())
                            .commit()
                    }
                    2 -> {

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, RealsFragment())
                            .commit()
                    }
                    3 -> {

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, NotficationFragment())
                            .commit()
                    }
                    4 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, ProfileFragment())
                            .commit()
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


    }


}