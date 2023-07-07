package com.example.assignnumbertoclient.blankSpace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @see #getCountOfAllBlankSpace()  calculate count of all {@link BlankSpaceContaianer} in databse
 * @see #getContainerThatIncludeNumber(Long) select a  {@link BlankSpaceContaianer} that include input number
 * @see #findTopByOrderByLeftSideAsc() select first  {@link BlankSpaceContaianer} for store new {@link com.example.assignnumbertoclient.client.Client}
 * */
@Repository
public interface BlankSpaceContainerDao extends JpaRepository<BlankSpaceContaianer, Long> {

    @Modifying
    @Query("update BlankSpaceContaianer continer set continer.rightSide = ?1, continer.leftSide=?2 where continer.id = ?3")
    int updateById(long rightSide, long leftSide, long id);


    @Query("SELECT count (BlankSpaceContaianer) from BlankSpaceContaianer ")
    Integer getCountOfAllBlankSpace();

    @Query("""
        select container from BlankSpaceContaianer container 
        where :clientNumber between container.leftSide and container.rightSide
    """)
    Optional<BlankSpaceContaianer> getContainerThatIncludeNumber(@Param("clientNumber") Long clientNumber);


    Optional<BlankSpaceContaianer> findTopByOrderByLeftSideAsc();


    @Modifying
    int removeById(long id);


    @Query(value = """
            SELECT container 
            from BlankSpaceContaianer container 
            where container.rightSide - container.leftSide > ?1 
            order by container.leftSide asc 
            LIMIT 1
            """)
    Optional<BlankSpaceContaianer> getFirstBlankSpaceThatCanContainAnArrayOfClients(long size);

}
