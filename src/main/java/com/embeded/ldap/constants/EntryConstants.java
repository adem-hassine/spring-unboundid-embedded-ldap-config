package com.embeded.ldap.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntryConstants {

    public static final String LDAP_ENTRY_OBJECT_CLASS = "person";
    public static final String USERNAME_ATTRIBUTE="sAMAccountName";
    public static final String FIRST_NAME_ATTRIBUTE="givenName";
    public static final String LAST_NAME_ATTRIBUTE="sn";
    public static final String FULL_NAME_ATTRIBUTE="name";
    public static final String TITLE_ATTRIBUTE="title";
    public static final String DEPARTMENT_ATTRIBUTE="department";
    public static final String MANAGER_ATTRIBUTE ="manager";
    public static final String GENDER ="sex";



}
