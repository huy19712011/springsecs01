package com.example.repository;

import com.example.model.Notice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    //@Query(value = "from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    //@Query(value = "from Notice")
    //List<Notice> findAllActiveNotices();

}
