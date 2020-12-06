package com.handcontrol.ui.main.gesturedetails

import androidx.lifecycle.*
import com.handcontrol.api.Api
import com.handcontrol.model.Gesture
import com.handcontrol.repository.GestureRepository
import kotlinx.coroutines.launch


class GestureDetailsViewModel(item: Gesture?) : ViewModel() {
    val id = item?.id
    val name = MutableLiveData(item?.name ?: "")
    val repeatCount = MutableLiveData(item?.repeatCount?.toString() ?: "")
    val actions = MutableLiveData(item?.actions ?: mutableListOf())
    val isInfinity = MutableLiveData(item?.isInfinityRepeat ?: false)
    var playedPosition: Int? = null
    var repeatCountInt = item?.repeatCount ?: 0

    private val infinityObserver =
        Observer<Boolean> { v ->
            if (v) repeatCount.value = "∞" else repeatCount.value = repeatCountInt.toString()
        }

    private val repeatObserver =
        Observer<String> { v ->
            if (isInfinity.value != true) repeatCountInt = v.toIntOrNull() ?: 0
        }

    init {
        isInfinity.observeForever(infinityObserver)
        repeatCount.observeForever(repeatObserver)
        GestureRepository.initGesture(item)
    }

    fun saveGesture() {
        viewModelScope.launch {
            Api.getApiHandler().saveGesture(
                Gesture(
                    id,
                    name.value!!,
                    false,
                    isInfinity.value!!,
                    repeatCount.value!!.toIntOrNull(),
                    actions.value!!
                )
            )
        }
    }

    override fun onCleared() {
        isInfinity.removeObserver(infinityObserver)
        repeatCount.removeObserver(repeatObserver)
        super.onCleared()
    }
}

class GestureDetailsViewModelFactory(
    private val gesture: Gesture?
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GestureDetailsViewModel(gesture) as T
    }
}
