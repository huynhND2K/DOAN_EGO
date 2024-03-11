package com.ego.doan_ego.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userGroup")
public class GroupUserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "join_date")
    private Long joinDate;
    @Column(name = "out_date")
    private Long outDate;

}
