package com.handcontrol.bluetooth

data class Packet(
    val type: Type,
    val payload: Collection<Byte>
) {
    enum class Type {
        EMPTY,
        ACK,
        ERR,
        TELEMETRY,
        GET_SETTINGS,
        SET_SETTINGS,
        GET_GESTURES,
        SAVE_GESTURE,
        DELETE_GESTURE,
        PERFORM_GESTURE_ID,
        PERFORM_GESTURE_RAW,
        SET_POSITIONS,
        UPDATE_LAST_TIME_SYNC,
        GET_TELEMETRY,
        START_TELEMETRY,
        STOP_TELEMETRY,
        GET_MIO_PATTERNS,
        SET_MIO_PATTERNS
    }
}