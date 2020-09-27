package com.example.urbandictionary.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.adapter.WordAdapter
import com.example.urbandictionary.databinding.FragmentMainBinding
import com.example.urbandictionary.utils.Constants
import com.example.urbandictionary.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var sharedPreferencesForText: SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var search: String
    private val wordAdapter = WordAdapter()
    private var likesSwitch = false
    private var dislikesSwitch = false

    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        setHasOptionsMenu(true)

        sharedPreferencesForText =
            activity?.getSharedPreferences(
                getString(R.string.sharedPrefFile),
                Context.MODE_PRIVATE
            )!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        likesSwitch = sharedPreferences.getBoolean("likes", false)
        dislikesSwitch = sharedPreferences.getBoolean("dislikes", false)

        val setData = sharedPreferencesForText.getString(getString(R.string.savedText), "Welcome")!!
        binding.editText.setText(setData)
        wordViewModel.refreshRepositoryData(setData)

        binding.recycler.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordAdapter
        }

        binding.button.setOnClickListener {
            search = binding.editText.text.toString()
            if (search.isEmpty()) {
                Toast.makeText(activity, "Enter the some text", Toast.LENGTH_SHORT).show()
            } else {
                wordViewModel.refreshRepositoryData(search)
                showProgressBar()
            }
        }

        wordViewModel.progressBarStatus.observe(viewLifecycleOwner, Observer { progressBar ->
            if (progressBar) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        })

        wordViewModel.wordList.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            if (it.isNullOrEmpty()) {
                hideProgressBar()
                observeIfListIsEmpty()
            } else {
                if (likesSwitch && !dislikesSwitch) {
                    val sortedList =
                        it.sortedWith(Comparator { word1, word2 ->
                            if (word1.thumbsUp > word2.thumbsUp) -1 else if (word1.thumbsUp < word2.thumbsUp) 1 else 0
                        })
                    wordAdapter.wordlist = sortedList
                }
                else if (dislikesSwitch && !likesSwitch) {
                    val sortedList =
                        it.sortedWith(Comparator { word1, word2 ->
                            if (word1.thumbsDown > word2.thumbsDown) -1 else if (word1.thumbsDown < word2.thumbsDown) 1 else 0
                        })
                    wordAdapter.wordlist = sortedList
                }
                else {
                    wordAdapter.wordlist = it
                }
            }
        })
    }

    private fun observeIfListIsEmpty() {
        wordViewModel.wordNotFound.observe(viewLifecycleOwner,
            Observer {  wordNotFound ->
                if (wordNotFound == Constants.EMPTY_WITH_NETWORK) {
                    wordNotFoundError()
                }
                if (wordNotFound == Constants.EMPTY_WITHOUT_NETWORK) {
                    networkError()
                }
            })
    }

    private fun networkError() {
        hideProgressBar()
        Toast.makeText(activity, "Network Error, Check your data connection", Toast.LENGTH_SHORT)
            .show()
    }

    private fun wordNotFoundError() {
        hideProgressBar()
        Toast.makeText(activity, "Sorry cannot find the given word", Toast.LENGTH_SHORT)
            .show()
    }

    private fun showProgressBar() {
        wordViewModel.progressBarOn()
    }

    private fun hideProgressBar() {
        wordViewModel.progressBarOff()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                findNavController().navigate(R.id.action_mainFragment_to_myPreferenceFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        with(sharedPreferencesForText.edit()) {
            val textToSave = binding.editText.text.toString()
            if (textToSave.isNotEmpty()) {
                putString(getString(R.string.savedText), textToSave)
                apply()
            }
        }
    }
}
