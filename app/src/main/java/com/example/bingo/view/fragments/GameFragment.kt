package com.example.bingo.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.bingo.constant.LONG_DURATION
import com.example.bingo.constant.SHORT_DURATION
import com.example.bingo.constant.URL_IMAGE_BACKGROUND
import com.example.bingo.constant.URL_IMAGE_COINS
import com.example.bingo.databinding.FragmentGameBinding
import com.example.bingo.presenter.adapter.AdapterGame
import com.example.bingo.presenter.adapter.EndGameInterface
import com.example.bingo.presenter.presenters.PresenterGame
import com.example.bingo.presenter.repository.Repository
import com.example.bingo.presenter.viewInterface.ViewGameInterface

class GameFragment : Fragment(), ViewGameInterface,EndGameInterface {

    private var binding: FragmentGameBinding? = null
    private val presenterGame: PresenterGame = PresenterGame(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterGame: AdapterGame
    private val repository = Repository()
    var initialX:Float = 0.0f
    var initialY:Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //всплывающее сообщение о стоимости попытки
        repository.showLongToast(requireContext(),"the attempt costs 25 coins")

        //привязка к recyclerView
        recyclerView = binding!!.idGameRv
        adapterGame = AdapterGame(requireContext(),this)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),4)
        recyclerView.adapter = adapterGame

        presenterGame.loadNewListNumbersForShowRecyclerView()

        //переменная для проверки окончания свайпа
        var isSwipeHandled = false

        //загрузка фоновой картинки
        binding!!.idGameImg.load(URL_IMAGE_BACKGROUND){ scale(Scale.FILL) }

        //загрузка картинки монеты
        binding!!.idGameImgMoney.load(URL_IMAGE_COINS){ scale(Scale.FIT) }

        //показ количества монет
        presenterGame.loadMyCoins()

        //обработка свайпа справа налево
        binding!!.idGameRv.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = motionEvent.x
                    initialY = motionEvent.y
                    isSwipeHandled = false
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = motionEvent.x - initialX
                    val deltaY = motionEvent.y - initialY
                    if (!isSwipeHandled && deltaX < 0 && Math.abs(deltaY) < Math.abs(deltaX) * 0.5f) {
                        presenterGame.loadNewListNumbersForShowRecyclerView()
                        isSwipeHandled = true
                    }
                    true
                }
                else -> false
            }
        }

        //выбрать текущий набор чисел
        binding!!.idGameButtonPick.setOnClickListener {
            if(repository.checkCoinsForGame()){
                //хватает денег на попытку
                binding!!.idGameButtonPick.isEnabled = false
                presenterGame.minusCoins()
                presenterGame.loadNewListNumbersForGameInRecyclerView(adapterGame.listNumbers)
            }else{
                //не хватает денег
                repository.showLongToast(requireContext(),"You have less than 25 coins.Top up your coins")
            }
        }

        //закончить игру+выход в меню
        binding!!.idGameButtonFinish.setOnClickListener {
            if(adapterGame.job.isActive){
                adapterGame.job.cancel()
            }
            repository.showMenuFragment()
        }

        //выход в меню
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if(adapterGame.job.isActive){
                adapterGame.job.cancel()
            }
            repository.showMenuFragment()
        }

    }

    //очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun showTextCoins(coins: Int) {
        binding!!.idGameTvMoney.text = "coins: $coins"
    }

    override fun showToast(context: Context, message: String, duration: String) {
        when(duration){
            SHORT_DURATION -> { Toast.makeText(context,message,Toast.LENGTH_SHORT).show() }
            LONG_DURATION -> { Toast.makeText(context,message,Toast.LENGTH_LONG).show() }
        }
    }

    override fun showNumbersInRecyclerView(listNumbers:List<Int>) {
        adapterGame.setListForShow(listNumbers)
    }

    @SuppressLint("SetTextI18n")
    override fun showNumbersForGameInRecyclerView(listNumbers: List<Int>, listHiddenNumbers:List<Int>) {
        binding!!.idGameTvNumbersBingo.text = "${listHiddenNumbers[0]} - ${listHiddenNumbers[1]} - ${listHiddenNumbers[2]} - ${listHiddenNumbers[3]}"
        adapterGame.setListHiddenNumbers(listHiddenNumbers)
        adapterGame.setList(listNumbers)
    }

    @SuppressLint("SetTextI18n")
    override fun gameWin() {
        binding!!.idGameTvDescSwipe.text = "BINGO! BINGO! BINGO!"
        presenterGame.addCoins()
        presenterGame.loadMyCoins()
    }

    @SuppressLint("SetTextI18n")
    override fun gameLoss() {
        binding!!.idGameTvDescSwipe.text = "You'll be lucky next time"
    }

    override fun goToMenu() {
        if(adapterGame.job.isActive){
            adapterGame.job.cancel()
        }
        repository.showMenuFragment()
    }

}