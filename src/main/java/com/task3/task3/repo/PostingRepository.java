package com.task3.task3.repo;


import com.task3.task3.models.Posting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;



@Repository
public interface PostingRepository extends CrudRepository<Posting, Long> {


    List<Posting> findByPstngdateBetween (LocalDate data, LocalDate data2);

    List<Posting> findByPstngdateBetweenAndAuthdelivery (LocalDate data, LocalDate data2, boolean status);

    List<Posting> findByAuthdelivery (boolean status);

}
