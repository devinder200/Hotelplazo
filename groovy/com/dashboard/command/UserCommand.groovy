package com.dashboard.command

import com.dashboard.model.User
import com.dashboard.util.Authority

class UserCommand extends BasicCommand implements Serializable {

    private static final long serialVersionUID = 91901774547107674L;

    String username
    String password
    String name
    String firstName
    String lastName
    List<Authority> authorities = []

    Boolean accountNonExpired
    Boolean accountNonLocked
    Boolean credentialsNonExpired
    Boolean isEnabled
    Boolean active

    public UserCommand() {
    }

    public UserCommand(User user) {
        this.id = user.id
        this.uuid = user.uuid
        this.username = user.username
        this.firstName = user.firstName
        this.lastName = user.lastName
        this.name = user.name
        this.dateCreated = user.dateCreated
        this.lastUpdated = user.lastUpdated
        this.authorities = user.authorities as List<Authority>
        this.accountNonExpired = user.accountNonExpired
        this.accountNonLocked = user.accountNonLocked
        this.credentialsNonExpired = user.credentialsNonExpired
        this.isEnabled = user.isEnabled
        this.active = user.active
    }


//    User populateUser(User existingUser) {
//        User user = existingUser ?: new User()
//        user.firstName = this.firstName
//        user.lastName = this.lastName
//        user.username = this.username
//        user.password = this.password
//        user.authorities = this.authorities
//        user.accountNonExpired = this.accountNonExpired
//        user.accountNonLocked = this.accountNonLocked
//        user.credentialsNonExpired = this.credentialsNonExpired
//        user.isEnabled = this.isEnabled
//        user.active = this.active
//        return user
//    }
}
