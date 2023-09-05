package com.example.menumanager.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.qfmenu.R
import com.example.qfmenu.SCREEN_LARGE
import com.example.qfmenu.viewmodels.models.Table
import com.example.qfmenu.databinding.FragmentWaitingTableBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WaitingTableFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WaitingTableFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentWaitingTableBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWaitingTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun popToStartDestination(navController: NavController) {
        val startDestination = navController.graph.startDestination
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()
        navController.navigate(startDestination, null, navOptions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isStartOrder = false

        val slidingPaneLayout =
            requireActivity().findViewById<SlidingPaneLayout>(R.id.sliding_pane_layout)
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.navBar)
        val width = resources.displayMetrics.widthPixels / resources.displayMetrics.density
        val spanCount = if (width > SCREEN_LARGE) 4 else 2
        val recyclerView = binding.tableRecyclerView

        val backMenu = navBar.menu.findItem(R.id.backToHome)
        val homeMenu = navBar.menu.findItem(R.id.homeMenu)
        val optionOne = navBar.menu.findItem(R.id.optionOne)
        val optionTwo = navBar.menu.findItem(R.id.optionTwo)

        backMenu.isVisible = isStartOrder
        homeMenu.isVisible = width < SCREEN_LARGE
        optionOne.isVisible = false
        optionTwo.isVisible = true

        homeMenu.setIcon(R.drawable.ic_home)
        backMenu.setIcon(R.drawable.ic_arrow_back)
        optionTwo.setIcon(R.drawable.ic_search)

        navBar.setOnItemSelectedListener {
            if (it.itemId == R.id.homeMenu) {
                slidingPaneLayout.closePane()
                navBar.visibility = View.GONE
            }
            if (it.itemId == R.id.optionTwo) {
            }
            if (it.itemId == R.id.backToHome) {
                findNavController().popBackStack()
            }
            true
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = WaitingTableAdapter(
            listOf(
                Table("1", "Free"),
                Table("2", "Free"),
            )
        )

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WaittingTableFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WaitingTableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}