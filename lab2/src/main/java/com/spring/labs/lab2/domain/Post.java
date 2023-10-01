package com.spring.labs.lab2.domain;

import java.time.LocalDate;
import java.util.Objects;

import lombok.NonNull;

public class Post {

	@NonNull
	private Long id;
//  TODO write the logic for Topic
//	private Topic topic;
//  TODO write the logic for Author
//  private User author; 
	private String content;
	private LocalDate creationDate;
	private int upvotes;
	private int downvotes;
//  private Topic parentTopic;  ???

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

	@Override
	public String toString() {
		return "Post [id=" + id + ", content=" + content + ", creationDate=" + creationDate + ", upvotes=" + upvotes
				+ ", downvotes=" + downvotes + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, creationDate, downvotes, id, upvotes);
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
				&& downvotes == other.downvotes && Objects.equals(id, other.id) && upvotes == other.upvotes;
	}
}
