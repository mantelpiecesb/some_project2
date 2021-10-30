package com.deitel.redtesttask1_dollarcoursechecker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Model of a one DollarCourseItem from BankService or Database
 */
@Entity(tableName = "dollar_course_records")
@Root(name = "Record", strict = false)
data class Record @JvmOverloads constructor (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @field:Attribute(name = "Date")
    var date: String? = null,

    @field:Element(name = "Nominal")
    var nominal : String? = null,

    @field:Element(name = "Value")
    var value : String? = null
)




