package com.demo.backend.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "first_name")
    var firstName: String?,

    @Column(name = "last_name")
    var lastName: String?,

    var email: String?,

    var role: String?,

    var title: String? = null,

    var usercode: String? = null,

    @Column(name = "create_dts", insertable = false, updatable = false)
    var createDts: LocalDateTime? = null,

    @Column(name = "update_dts")
    var updateDts: LocalDateTime? = null
) {

    constructor(
        firstName: String, lastName: String, email: String, role: String, title: String?, usercode: String?
    ) : this(null, firstName, lastName, email, role, title, usercode)

    class Builder {
        private var id: Long? = null
        private var firstName: String? = null
        private var lastName: String? = null
        private var email: String? = null
        private var role: String? = null
        private var title: String? = null
        private var usercode: String? = null

        fun id(id: Long?): Builder {
            this.id = id
            return this
        }

        fun firstName(firstName: String?): Builder {
            this.firstName = firstName
            return this
        }

        fun lastName(lastName: String?): Builder {
            this.lastName = lastName
            return this
        }

        fun email(email: String?): Builder {
            this.email = email
            return this
        }

        fun role(role: String?): Builder {
            this.role = role
            return this
        }

        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun usercode(usercode: String?): Builder {
            this.usercode = usercode
            return this
        }

        fun build() = User(id, firstName, lastName, email, role, title, usercode)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (role != other.role) return false
        if (title != other.title) return false
        if (usercode != other.usercode) return false
        if (createDts != other.createDts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (role?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (usercode?.hashCode() ?: 0)
        result = 31 * result + (createDts?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "User(id=$id, firstName=$firstName, lastName=$lastName, email=$email, role=$role, title=$title, usercode=$usercode, createDts=$createDts, updateDts=$updateDts)"
    }

}