package com.levi9.demo.wallet.repository;

import com.levi9.demo.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
