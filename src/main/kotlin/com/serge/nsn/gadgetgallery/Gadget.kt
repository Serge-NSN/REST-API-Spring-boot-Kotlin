package com.serge.nsn.gadgetgallery

import jakarta.persistence.*


class Gadget {
    @Entity
    @Table(name = "GADGET")
    data class Gadget(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val  gadgetId: Long,
        val gadgetName: String,
        val gadgetCategory: String?,
        val gadgetAvailability: Boolean = true,
        val gadgetPrice: Double

        )
}