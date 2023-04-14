package com.embeded.ldap.entry;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

import static com.embeded.ldap.constants.EntryConstants.*;

@Entry(objectClasses = LDAP_ENTRY_OBJECT_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class UserEntry {
    @Id
    @JsonIgnore
    private Name cName;
    @Attribute (name = FIRST_NAME_ATTRIBUTE)
    private String firstName;
    @Attribute (name = USERNAME_ATTRIBUTE)
    private String username;
    @Attribute(name = MANAGER_ATTRIBUTE)
    private String managerDn;
    @Attribute(name = TITLE_ATTRIBUTE)
    private String title;
    @Attribute(name = LAST_NAME_ATTRIBUTE)
    private String lastName;
    @Attribute(name = FULL_NAME_ATTRIBUTE)
    private String fullName;
    @Attribute(name = DEPARTMENT_ATTRIBUTE)
    private String departmentName;

}
