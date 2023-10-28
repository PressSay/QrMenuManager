package com.example.qfmenu.ui.review.store.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.qfmenu.QrMenuApplication
import com.example.qfmenu.R
import com.example.qfmenu.SCREEN_LARGE
import com.example.qfmenu.databinding.FragmentReviewStoreBinding
import com.example.qfmenu.util.NavGlobal
import com.example.qfmenu.util.StoDisReviewAdapter
import com.example.qfmenu.viewmodels.SaveStateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewStoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentReviewStoreBinding? = null
    private val binding get() = _binding!!
    private val saveStateViewModel: SaveStateViewModel by activityViewModels()

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
        _binding = FragmentReviewStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewReviewStoreList = binding.recyclerViewReviewStoreList
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.navBar)
        val width: Float = resources.displayMetrics.widthPixels / resources.displayMetrics.density
        val spanCount = if (width < SCREEN_LARGE) 1 else 2
        val slidePaneLayout =
            requireActivity().findViewById<SlidingPaneLayout>(R.id.sliding_pane_layout)

        val navGlobal = NavGlobal(navBar, findNavController(), slidePaneLayout, saveStateViewModel) {
            if (it == R.id.optionOne) {
            }
            if (it == R.id.optionTwo) {
                findNavController().navigate(R.id.action_reviewStoreFragment_to_satisfactionFragment)
            }
        }
        navGlobal.setIconNav(R.drawable.ic_arrow_back, R.drawable.ic_home, R.drawable.ic_thumb_down, R.drawable.ic_plus)
        navGlobal.setVisibleNav(true, width < SCREEN_LARGE, true, optTwo = true)
        navGlobal.impNav()

        val reviewDao = (activity?.application as QrMenuApplication).database.reviewDao()

        val stoDisReviewAdapter = StoDisReviewAdapter(
        true, reviewDao, saveStateViewModel, requireContext()
        )


        recyclerViewReviewStoreList.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerViewReviewStoreList.adapter = stoDisReviewAdapter

        reviewDao.getReviewCustomerCrossRefs().observe(this.viewLifecycleOwner) {
            it.let {
                stoDisReviewAdapter.submitList(it)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReviewStoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewStoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}