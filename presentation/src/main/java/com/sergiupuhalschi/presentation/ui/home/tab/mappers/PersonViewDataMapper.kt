package com.sergiupuhalschi.presentation.ui.home.tab.mappers

import com.sergiupuhalschi.common.utils.NumberFormatUtils
import com.sergiupuhalschi.common.utils.OneWayMapper
import com.sergiupuhalschi.presentation.ui.home.tab.models.PersonViewData
import com.sergiupuhalschi.repository.person.models.Person

class PersonViewDataMapper(
    private val numberFormatUtils: NumberFormatUtils
) : OneWayMapper<List<Person>, List<PersonViewData>> {

    override fun from(input: List<Person>): List<PersonViewData> {
        return input.map {
            PersonViewData(
                id = it.id,
                name = it.getFullName(),
                username = "@${it.username}",
                picture = it.picture,
                netWorth = "$${numberFormatUtils.format(it.netWorth, true)}"
            )
        }
    }
}