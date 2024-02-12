package com.sergiupuhalschi.common.utils

interface OneWayMapper<InputType, OutputType> {

    fun from(input: InputType): OutputType
}