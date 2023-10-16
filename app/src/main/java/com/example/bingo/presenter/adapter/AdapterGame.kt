package com.example.bingo.presenter.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bingo.R
import com.example.bingo.presenter.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdapterGame(private val context: Context,private val intrfc:EndGameInterface):RecyclerView.Adapter<AdapterGame.GameViewHolder>() {

    var listNumbers = emptyList<Int>()
    private var listHiddenNumbers = emptyList<Int>()
    private val repository = Repository()
    private var flagOnlyShowNumbers = true
    var job:Job = Job()

    class GameViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.id_item_rv_tv)
        textView.text = listNumbers[position].toString()
    }

    override fun getItemCount(): Int {
        return listNumbers.size
    }

    override fun onViewAttachedToWindow(holder: GameViewHolder) {
        super.onViewAttachedToWindow(holder)

        if(!flagOnlyShowNumbers){
            flagOnlyShowNumbers = true
            job = CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                if(listNumbers.slice(0..3).toList() == listHiddenNumbers){
                    //есть совпадение
                    repository.showToast(context,"BINGO!THE FIRST LINE!")
                    intrfc.gameWin()
                    delay(3500)
                    intrfc.goToMenu()
                }else{
                    if(listNumbers.slice(4..7).toList() == listHiddenNumbers){
                        //есть совпадение
                        repository.showToast(context,"BINGO!SECOND LINE!")
                        intrfc.gameWin()
                        delay(3500)
                        intrfc.goToMenu()
                    }else{
                        if(listNumbers.slice(8..11).toList() == listHiddenNumbers){
                            //есть совпадение
                            repository.showToast(context,"BINGO!THE THIRD LINE!")
                            intrfc.gameWin()
                            delay(3500)
                            intrfc.goToMenu()
                        }else{
                            if(listNumbers.slice(12..15).toList() == listHiddenNumbers){
                                //есть совпадение
                                repository.showToast(context,"BINGO!THE FOURTH LINE!")
                                intrfc.gameWin()
                                delay(3500)
                                intrfc.goToMenu()
                            }else{
                                if(listNumbers[0] == listHiddenNumbers[0] && listNumbers[4] == listHiddenNumbers[1] &&
                                    listNumbers[8] == listHiddenNumbers[2] && listNumbers[12] == listHiddenNumbers[0]){
                                    //есть совпадение
                                    repository.showToast(context,"BINGO!FIRST COLUMN!")
                                    intrfc.gameWin()
                                    delay(3500)
                                    intrfc.goToMenu()
                                }else{
                                    if(listNumbers[1] == listHiddenNumbers[0] && listNumbers[5] == listHiddenNumbers[1] &&
                                        listNumbers[9] == listHiddenNumbers[2] && listNumbers[13] == listHiddenNumbers[0]){
                                        //есть совпадение
                                        repository.showToast(context,"BINGO!SECOND COLUMN!")
                                        intrfc.gameWin()
                                        delay(3500)
                                        intrfc.goToMenu()
                                    }else{
                                        if(listNumbers[2] == listHiddenNumbers[0] && listNumbers[6] == listHiddenNumbers[1] &&
                                            listNumbers[10] == listHiddenNumbers[2] && listNumbers[14] == listHiddenNumbers[0]){
                                            //есть совпадение
                                            repository.showToast(context,"BINGO!THE THIRD COLUMN!")
                                            intrfc.gameWin()
                                            delay(3500)
                                            intrfc.goToMenu()
                                        }else{
                                            if(listNumbers[3] == listHiddenNumbers[0] && listNumbers[7] == listHiddenNumbers[1] &&
                                                listNumbers[11] == listHiddenNumbers[2] && listNumbers[15] == listHiddenNumbers[0]){
                                                //есть совпадение
                                                repository.showToast(context,"BINGO!THE FOURTH COLUMN!")
                                                intrfc.gameWin()
                                                delay(3500)
                                                intrfc.goToMenu()
                                            }else{
                                                //нет нигде совпадений
                                                repository.showToast(context,"bad luck...")
                                                intrfc.gameLoss()
                                                delay(3500)
                                                intrfc.goToMenu()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<Int>){
        listNumbers = list
        flagOnlyShowNumbers = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListForShow(list:List<Int>){
        listNumbers = list
        notifyDataSetChanged()
    }

    fun setListHiddenNumbers(list:List<Int>){
        listHiddenNumbers = list
    }

}


