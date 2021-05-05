package com.handcontrol.api

import android.util.Log
import com.handcontrol.bluetooth.*
import com.handcontrol.model.Action
import com.handcontrol.model.Gesture
import com.handcontrol.server.protobuf.Settings
import com.handcontrol.server.protobuf.Stream
import com.handcontrol.server.protobuf.Uuid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.*

/**
 * BluetoothHandler - класс обработчика Bluetooth соединения
 */
class BluetoothHandler(private val macAddress: String) : IApiHandler {
    private val bluetoothService =  BluetoothService(macAddress).apply { start() }

    /**
     * isConnected - функция подтверждения соединения
     */
    fun isConnected() : Boolean {
        if (bluetoothService.state == BluetoothService.State.CONNECTED) {
            return true
        }

        return false
    }

    fun close() = bluetoothService.close()

    private suspend fun request(request: Packet): Packet {
        if (isConnected()) {
            var response: Packet? = null
            val observer = object : Observer {
                override fun update(p0: Observable?, list: Any?) {
                    if (list is MutableList<*>) {
                        list.forEach {
                            if (it is Packet) {
                                if (it.type == request.type) {
                                    response = it
                                    list.remove(it)
                                    return
                                } else if (it.type == Packet.Type.ERR) {
                                    list.remove(it)
                                    throw HandlingException()
                                }
                            }
                        }
                    }
                }
            }
            bluetoothService.mReadPackets.addObserver(observer)
            bluetoothService.write(request)
            response?.let { return it }
            repeat(25) {
                delay(200)
                response?.let { return it }
                if (bluetoothService.state == BluetoothService.State.DISCONNECTED)
                    throw DisconnectedException()
            }
            throw TimeoutException()
        }

        throw ConnectingFailedException()
    }

    override suspend fun getTelemetry(): Iterator<Stream.PubReply> {
        TODO("Not yet implemented")
    }

    override suspend fun getSettings(): Settings.GetSettings {
        TODO("Not yet implemented")
    }

    override suspend fun setSettings(settings: Settings.SetSettings) {
        TODO("Not yet implemented")
    }

    override suspend fun getGestures(): MutableList<Gesture> {
        return withContext(Dispatchers.IO) {
            val res = request(Packet(Packet.Type.GET_GESTURES, emptyList()))

            mutableListOf()
        }
    }

    override suspend fun saveGesture(gesture: Gesture) {
        return withContext(Dispatchers.IO) {
            val byteArray: MutableList<Byte> = mutableListOf()
            gesture.getProtoModel().toByteArray().forEach {
                byteArray.add(it)
            }
            request(Packet(Packet.Type.SAVE_GESTURE, byteArray))
        }
    }

    override suspend fun deleteGesture(gestureId: Uuid.UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun performGestureId(gesture: Gesture) {
        TODO("Not yet implemented")
    }

    override suspend fun performGestureRaw(gesture: Gesture) {
        TODO("Not yet implemented")
    }

    override suspend fun setPositions(action: Action) {
        TODO("Not yet implemented")
    }
}