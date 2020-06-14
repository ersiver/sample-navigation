package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
                inflater, R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener { view: View ->
            view.findNavController()
                    .navigate(
                            TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }


    //Navigation using the options menu: set the MenuItem id matching fragment name,
    //and the onNavDestinationSelected does the rest (here: "@+id/aboutFragment")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}
