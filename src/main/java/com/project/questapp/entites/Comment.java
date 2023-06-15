package com.project.questapp.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tbl_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false) //postgredeki kolona bağladık.
    @OnDelete(action = OnDeleteAction.CASCADE) // bir user silindiğinde tüm postlarınıda sil.
    @JsonIgnore //serilizable işlemlerinde sıkıntı cıkarmaması için
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false) //postgredeki kolona bağladık.
    @OnDelete(action = OnDeleteAction.CASCADE) // bir user silindiğinde tüm postlarınıda sil.
    @JsonIgnore //serilizable işlemlerinde sıkıntı cıkarmaması için
    private User user;

    @Column(columnDefinition = "text")
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
