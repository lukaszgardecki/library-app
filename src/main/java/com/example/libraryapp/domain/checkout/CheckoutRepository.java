package com.example.libraryapp.domain.checkout;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckoutRepository extends CrudRepository<Checkout, Long> {

    List<Checkout> findCheckoutsByBook_Id(Long bookId);
}
