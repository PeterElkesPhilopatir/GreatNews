package com.peter.greatnews.framework.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.peter.greatnews.databinding.FragmentDetailsBinding
import com.peter.greatnews.framework.presentation.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: NewsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "BREAKING NEWS! ${viewModel.selectedArticle.value!!.title}"
            )
            shareIntent.putExtra(Intent.EXTRA_TEXT, viewModel.selectedArticle.value!!.url)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        binding.btnMore.setOnClickListener {
            val uri: Uri =
                Uri.parse(viewModel.selectedArticle.value!!.url) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.btnFav.setOnClickListener { viewModel.saveToFavorites() }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        return binding.root

    }

}