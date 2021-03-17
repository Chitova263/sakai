package org.sakaiproject.conversations.api.model;

import java.util.ArrayList;
import java.util.List;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import org.sakaiproject.conversations.api.TopicType;

import lombok.Getter;

@Entity
@Table(name = "CONV_TOPICS")
@Getter
public class Topic {

    @Id
    @Column(name = "TOPIC_ID", length = 36, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "ABOUT_REFERENCE", length = 255, nullable = false)
    private String aboutReference;

    @Column(name = "TITLE", length = 255, nullable = false)
    private String title;

    @Column(name = "BODY", nullable = false)
    private String body;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicPoster> posters = new ArrayList<>();

    @Column(name = "TYPE", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private TopicType type;

    @Column(name = "RESOLVED")
    private Boolean resolved = Boolean.FALSE;

    @Column(name = "NUMBER_OF_POSTS")
    private Integer numberOfPosts = 0;

    @Column(name = "HOW_ACTIVE")
    private Integer howActive = 0;

    @Column(name = "LAST_ACTIVITY")
    private Instant lastActivity;

    @Column(name = "PINNED")
    private Boolean pinned = Boolean.FALSE;

    @Embedded
    private Metadata metadata;
}
