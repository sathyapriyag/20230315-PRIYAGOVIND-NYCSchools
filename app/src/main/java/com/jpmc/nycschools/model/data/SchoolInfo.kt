package com.jpmc.nycschools.model.data

import java.io.Serializable

data class SchoolInfo(val dbn: String,
val school_name: String,
val overview_paragraph: String,
val academicopportunities1: String,
val academicopportunities2: String,
val location: String,
val phone_number: String,
val fax_number: String,
val school_email: String,
val website: String
): Serializable
