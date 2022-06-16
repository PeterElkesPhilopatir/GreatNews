package com.peter.greatnews.framework.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.peter.greatnews.R
import com.peter.greatnews.databinding.FragmentFavoritesBinding
import com.peter.greatnews.databinding.FragmentMainBinding
import com.peter.greatnews.framework.presentation.NewsViewModel
import com.peter.greatnews.framework.presentation.main.ArticlesAdapter
import com.peter.greatnews.framework.presentation.main.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.loadFavorites()

        val adapter = ArticlesAdapter(OnClickListener {
            viewModel.selectArticle(it)
            viewModel.navigateToDetails()
        })

        viewModel.favorites.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        }


        return binding.root
    }

}