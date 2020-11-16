package com.handcontrol.ui.main.gestures.gesturedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.handcontrol.R
import com.handcontrol.base.BaseFragment
import com.handcontrol.databinding.FragmentGestureDetailsEditorBinding
import com.handcontrol.model.Gesture
import com.handcontrol.ui.main.gestures.gesturedetails.GestureDetailsFragment.Companion.ARG_GESTURE_KEY

class GestureDetailsEditorFragment
    : BaseFragment<FragmentGestureDetailsEditorBinding, GestureDetailsViewModel>(
    GestureDetailsViewModel::class.java,
    R.layout.fragment_gesture_details_editor
) {

//    private val PERMISSIONS_RECORD_AUDIO = 200

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = GestureDetailsViewModelFactory(
            arguments?.getSerializable(ARG_GESTURE_KEY) as? Gesture
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        with(editableActionsRecycler) {
//            adapter = BaseRecyclerAdapter<Action, ExecutableItemListener>(
//                R.layout.list_item_executable,
//                viewModel.actions.value!!,
//                object : ExecutableItemListener {
//                    override fun onClick(item: ExecutableItem) {
//                        //TODO("Not yet implemented")
//                    }
//
//                    override fun onPlay(item: ExecutableItem) {
//                        //TODO("Not yet implemented")
//                    }
//
//                }
//            )
//
//            layoutManager = LinearLayoutManager(context).apply {
//                orientation = LinearLayoutManager.VERTICAL
//            }
//        }
    }

    //  Вставьте фрагмент кода, когда пользователь захочет сохранить график
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    == PackageManager.PERMISSION_DENIED
//    ) {
//        // Запрос разрешения
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//            PERMISSIONS_WRITE_EXTERNAL_STORAGE
//        )
//    } else {
//        //исполняемый код
//    }
}