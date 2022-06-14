package ru.kirill.recycler_view.simplerecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kirill.recycler_view.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    val list = arrayListOf(
        Data("Header","", TYPE_HEADER),
        Data("Earth", "Earth Description", TYPE_EARTH),
        Data("Mars", "Mars Description", TYPE_MARS),
        Data("Earth", "Earth Description", TYPE_EARTH),
        Data("Earth", "Earth Description", TYPE_EARTH),
        Data("Mars", "Mars Description", TYPE_MARS),
        Data("Mars", "Mars Description", TYPE_MARS),
        Data("Earth", "Earth Description", TYPE_EARTH),
        Data("Mars", "Mars Description", TYPE_MARS)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.adapter = RecyclerViewAdapter(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}