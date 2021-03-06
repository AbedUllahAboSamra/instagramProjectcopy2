package com.example.instagramproject.fragment

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.instagramproject.R
import com.example.instagramproject.ScandryFragments.*
import com.example.instagramproject.databinding.FragmentMainBinding
import com.example.instagramproject.editorStory.PhotoApp
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
        val nightModeFlags =
            view!!.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        binding.tabTapLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, SubMainFragment())
                            .commit()

                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                binding.tabTapLayout.setBackgroundColor(Color.WHITE)

                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
                        }

                        }
                    1 -> {
                         requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, SearchFragment())
                            .commit()


                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                binding.tabTapLayout.setBackgroundColor(Color.WHITE)

                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
                        }

                    }



                    2 -> {

                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, RealsFragment())
                            .commit()


                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                binding.tabTapLayout.setBackgroundColor(Color.rgb(176, 176, 176))

                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
                        }

                    }


                    3 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, NotficationFragment())
                            .commit()

                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                binding.tabTapLayout.setBackgroundColor(Color.WHITE)
                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
                        }
                    }
                    4 -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.con_FragmentContainer, ProfileFragment())
                            .commit()

                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                binding.tabTapLayout.setBackgroundColor(Color.WHITE)

                            }
                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
                        }
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