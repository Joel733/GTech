package com.gtech.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserTransaction {
	@EmbeddedId
	private UserTransaction_PK pk;
	public UserTransaction_PK getPk() {
		return pk;
	}
	public void setPk(UserTransaction_PK pk) {
		this.pk = pk;
	}
}
