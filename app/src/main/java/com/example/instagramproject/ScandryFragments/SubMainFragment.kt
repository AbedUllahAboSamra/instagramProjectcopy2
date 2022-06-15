package com.example.instagramproject.ScandryFragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.R
import com.example.instagramproject.adapter.AdapterRec_Story
import com.example.instagramproject.databinding.FragmentSubMainBinding
import com.example.instagramproject.screen.FirstCreatePostActivity
import com.example.instagramproject.screen.MainActivity

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
        binding.recStoryItems.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        var storyAdapter = AdapterRec_Story(requireContext(),array)
        binding.recStoryItems.adapter=storyAdapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
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