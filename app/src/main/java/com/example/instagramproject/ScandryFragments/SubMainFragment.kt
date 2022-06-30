package com.example.instagramproject.ScandryFragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.adapter.AdapterRec_Posts
import com.example.instagramproject.adapter.AdapterRec_Story
import com.example.instagramproject.adapter.adapterPagerFilesPick
import com.example.instagramproject.databinding.FragmentSubMainBinding
import com.example.instagramproject.model.UserModel
import com.example.instagramproject.screen.FirstCreatePostActivity
import com.example.instagramproject.screen.MainActivity
import com.example.instagramproject.screen.SplachActivity

class SubMainFragment : Fragment() {

    lateinit var binding: FragmentSubMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubMainBinding.inflate(layoutInflater)

        (requireActivity() as? MainActivity)?.setSupportActionBar(requireActivity().findViewById(R.id.to_ToolBar))
        setHasOptionsMenu(true)
        binding.toToolBar.inflateMenu(R.menu.tool_bar_menu)
        //  requireActivity().registerForContextMenu(requireActivity().findViewById(R.id.meny))

        var array = ArrayList<String>()
        array.add("")
        array.add("")
        array.add("")
        array.add("")
        array.add("")
        array.add("")

        var arr = ArrayList<UserModel>()



        // story Recycle view Adapter
        binding.recStoryItems.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var storyAdapter = AdapterRec_Story(requireContext(), arr)
        binding.recStoryItems.adapter = storyAdapter

        // posts Recycle view Adapter

        var postsAdapter = AdapterRec_Posts(requireContext(), SplachActivity.postsArray)
        var layManeger = LinearLayoutManager(requireContext())
        binding.recPosts.layoutManager = layManeger
        binding.recPosts.adapter = postsAdapter

// on scroled
        binding.recPosts.viewTreeObserver.addOnScrollChangedListener {
            try {
//         if (adapterPagerFilesPick.amp.isPlaying == true) {
//             Handler().postDelayed({
//
//                 adapterPagerFilesPick.amp.pause()
//
//             }, 100)
//         }
            } catch (s: Exception) {
                Log.e("ASD", s.toString())
            }

        }

        return binding.root
    }

    override fun onStart() {

        super.onStart()
        binding.laySwipeRefreshLayout.isRefreshing = false
        // on layout refresh
        binding.laySwipeRefreshLayout.setOnRefreshListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    FirstCreatePostActivity::class.java
                )
            )
        }


    }

    override fun onResume() {
        binding.laySwipeRefreshLayout.isRefreshing = false
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_add -> {}
            R.id.btn_chat -> {}
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        requireActivity().menuInflater.inflate(R.menu.context_menu_title, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }

}