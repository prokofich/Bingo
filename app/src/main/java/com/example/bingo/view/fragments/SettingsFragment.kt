package com.example.bingo.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import coil.load
import coil.size.Scale
import com.example.bingo.constant.LONG_DURATION
import com.example.bingo.constant.SHORT_DURATION
import com.example.bingo.constant.URL_IMAGE_BACKGROUND
import com.example.bingo.constant.URL_IMAGE_COINS
import com.example.bingo.databinding.FragmentSettingsBinding
import com.example.bingo.presenter.presenters.PresenterSettings
import com.example.bingo.presenter.repository.Repository
import com.example.bingo.presenter.viewInterface.ViewSettingsInterface

class SettingsFragment : Fragment(),ViewSettingsInterface {

    private var binding: FragmentSettingsBinding? = null
    private val presenterSettings:PresenterSettings = PresenterSettings(this)
    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка фоновой картинки
        binding!!.idSettingsImg.load(URL_IMAGE_BACKGROUND){ scale(Scale.FILL) }

        //загрузка картинки монеты
        binding!!.idSettingsImgMoney1.load(URL_IMAGE_COINS){ scale(Scale.FIT) }
        binding!!.idSettingsImgMoney2.load(URL_IMAGE_COINS){ scale(Scale.FIT) }

        //загрузка количества монет
        presenterSettings.loadMyCoins()

        //загрузка текста правил игры
        presenterSettings.loadTextRules()

        //вернуться в меню
        binding!!.idSettingsButtonBack.setOnClickListener {
            repository.showMenuFragment()
        }

        //вернуться в меню
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository.showMenuFragment()
        }

        //пополнение монет
        binding!!.idSettingsButtonAddMoney.setOnClickListener {
            if(presenterSettings.checkLastDay()){
                //можно
                presenterSettings.loadNewCoinsForReplenish(requireContext())
            }else{
                //нельзя
                showToast(requireContext(),"try it tomorrow", LONG_DURATION)
                showMessageForCancelReplenish("the prize will be tomorrow")
            }
        }

    }

    //очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showTextRules(text: String) {
        binding!!.idSettingsTvRules.text = text
    }

    @SuppressLint("SetTextI18n")
    override fun showTextCoins(coins: Int) {
        binding!!.idSettingsTvCoins.text = "your coins:$coins"
    }

    @SuppressLint("SetTextI18n")
    override fun showMessageForReplenish(coins: Int) {
        binding!!.idSettingsTvMessage.text = "replenishment for $coins coins"
    }

    override fun showMessageForCancelReplenish(text: String) {
        binding!!.idSettingsTvMessage.text = text
    }

    override fun showToast(context: Context, message: String, duration: String) {
        when(duration){
            SHORT_DURATION -> { Toast.makeText(context,message,Toast.LENGTH_SHORT).show() }
            LONG_DURATION -> { Toast.makeText(context,message,Toast.LENGTH_LONG).show() }
        }
    }

}