package com.embeded.ldap.entry;


import org.springframework.data.ldap.repository.LdapRepository;

public interface UserEntryRepository extends LdapRepository<UserEntry> {
}
