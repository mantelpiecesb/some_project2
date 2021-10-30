package com.deitel.redtesttask1_dollarcoursechecker.api

import com.deitel.redtesttask1_dollarcoursechecker.model.Record
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * response from API-query to get dollar-rouble course
 */
@Root(name = "ValCurs", strict = false)
class BankResponse{
    @field:ElementList(name = "Record", inline = true)
    var records: MutableList<Record> = mutableListOf()
}


