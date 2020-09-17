package com.test.demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

	@Id
	private long id;

	@OneToMany
	@JoinColumn(name = "UserID", nullable = false)
	private List<Tweet> tweets = new ArrayList<>();
}

@Entity
class Tweet {

	@Id
	@Column(name = "TweetID")
	private long id;
}