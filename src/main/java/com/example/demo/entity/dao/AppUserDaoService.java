package com.example.demo.entity.dao;

import com.example.demo.auth.ApplicationUser;
import com.example.demo.auth.ApplicationUserDao;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("database_user")
public class AppUserDaoService implements ApplicationUserDao {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserDaoService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByName(String email) {
        AppUser appUser = appUserRepository .findAppUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("email %s not found", email)));
        final ApplicationUser applicationUser = new ApplicationUser(
                appUser.applicationRole.getGrantedPermission(),
                appUser.password,
                appUser.username,
                true,
                true,
                true,
                true
        );
        return Optional.of(applicationUser);
    }
}
