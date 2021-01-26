package com.trip.my.room.server.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User {

    @Id
    @GeneratedValue(GenerationType.AUTO)
    var id: Long? = null

    var username: String? = null

    var email: String? = null

    var social: String? = null

}
