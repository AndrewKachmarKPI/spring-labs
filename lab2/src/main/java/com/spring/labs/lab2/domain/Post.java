package com.spring.labs.lab2.domain;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Post {
	@Id
	private Long id;
//  TODO write the logic for Topics
//	private List<Topic> topics;
//  TODO write the logic for Authors
//  private List<User> authors; 
	private String name;
	private String content;
	private LocalDate creationDate;
	private int upvotes;
	private int downvotes;

	public Post() {
	}

	public Post(Long id, String name, String content, LocalDate creationDate, int upvotes, int downvotes) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.creationDate = creationDate;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", name=" + name + ", content=" + content + ", creationDate=" + creationDate
				+ ", upvotes=" + upvotes + ", downvotes=" + downvotes + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, creationDate, downvotes, id, name, upvotes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(creationDate, other.creationDate)
				&& downvotes == other.downvotes && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& upvotes == other.upvotes;
	}
}
