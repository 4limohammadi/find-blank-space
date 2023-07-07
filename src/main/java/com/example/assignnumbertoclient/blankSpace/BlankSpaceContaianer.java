package com.example.assignnumbertoclient.blankSpace;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "TBL_BLANK_SPACE_CONTAINER")
public class BlankSpaceContaianer {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "left_side")
    private long leftSide;

    @Column(name= "right_side")
    private long rightSide;
}
