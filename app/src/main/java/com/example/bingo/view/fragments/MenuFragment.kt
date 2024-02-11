package com.example.bingo.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import coil.load
import coil.size.Scale
import com.example.bingo.constant.URL_IMAGE_BACKGROUND
import com.example.bingo.databinding.FragmentMenuBinding
import com.example.bingo.presenter.repository.Repository

class MenuFragment : Fragment(){

    private var binding: FragmentMenuBinding? = null
    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка фоновой картинки
        binding?.idMenuImg?.load(URL_IMAGE_BACKGROUND){scale(Scale.FILL)}

        //переход к игре
        binding?.idMenuButtonPlay?.setOnClickListener {
            repository.showGameFragment()
        }

        //переход к настройкам
        binding?.idMenuButtonSettings?.setOnClickListener {
            repository.showSettingsFragment()
        }

        //выход из игры
        binding?.idMenuButtonQuit?.setOnClickListener {
            repository.exitingTheApplication()
        }

        //выход из игры
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository.exitingTheApplication()
        }

    }

    //очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}