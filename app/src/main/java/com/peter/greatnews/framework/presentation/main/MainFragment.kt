package com.peter.greatnews.framework.presentation.main

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.ui.BiasAlignment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.peter.greatnews.R
import com.peter.greatnews.databinding.FragmentMainBinding
import com.peter.greatnews.framework.presentation.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        val adapter = ArticlePagingAdapter(OnClickListener {
            viewModel.selectArticle(it)
            viewModel.navigateToDetails()
        })
        viewModel.setQuery("egypt")
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel!!.setQuery(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel!!.setQuery(query)
                    return true
                }
            })

            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = ArticlesLoadStateAdapter()
            )
        }

        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment()
                )
                viewModel.onDoneNavigateToDetails()
            }
        }
        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

}