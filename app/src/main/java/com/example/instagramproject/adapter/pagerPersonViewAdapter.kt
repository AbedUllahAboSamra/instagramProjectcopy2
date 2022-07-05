package com.example.instagramproject.adapter

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.instagramproject.ScandryFragments.SubMainFragment
import com.example.instagramproject.databinding.DesignShowStoryItemBinding
import com.example.instagramproject.databinding.ShowSotryItemBinding
import com.example.instagramproject.model.StoryModle
import com.example.instagramproject.model.userStoryModle
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


// to pagerPersonView
class pagerPersonViewAdapter(var context: Context, var storys: ArrayList<StoryModle>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return storys.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var binding = ShowSotryItemBinding.inflate(LayoutInflater.from(context))
        Picasso.get().load(storys[position].imageOrVedioUrl).into(binding.imageItem)
        Log
            .e("ASD", storys[position].id + "SSSSSSSSSSSD")
        FirebaseFirestore.getInstance()
            .collection("story")
            .document(storys[position].id)
            .collection("seenIds")
            .document(SplachActivity.uId)
            .set(hashMapOf("a" to "s"))
            .addOnSuccessListener {
                SplachActivity.storyes.forEach { i ->
                    if (i.id==storys[position].id){
                        i.seenIds.add(SplachActivity.uId)
                        SubMainFragment.storyAdapter.notfiy()
                    }

                }

            }

        container.addView(binding.root)
        return binding.root
    }

}