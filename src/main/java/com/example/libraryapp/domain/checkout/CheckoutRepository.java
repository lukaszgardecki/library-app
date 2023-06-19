package com.example.libraryapp.domain.checkout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    List<Checkout> findCheckoutsByBook_Id(Long bookId);
}
