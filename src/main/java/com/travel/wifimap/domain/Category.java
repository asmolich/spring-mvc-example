package com.travel.wifimap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CATEGORY")
// @DynamoDBTable(tableName = "T_CATEGORY")
public class Category {

	private String id;
	private String name;
	private String pluralName;
	// private String shortName;
	private String icon;

	public Category() {
		super();
	}

	@Id
	@Column(name = "ID")
	// @GeneratedValue(generator = "uuid")
	// @GenericGenerator(name = "uuid", strategy = "uuid")
	// @DynamoDBHashKey(attributeName = "ID")
	// @DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME")
	// @DynamoDBAttribute(attributeName = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PLURAL_NAME")
	// @DynamoDBAttribute(attributeName = "PLURAL_NAME")
	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	// @DynamoDBAttribute(attributeName = "SHORT_NAME")
	// public String getShortName() {
	// return shortName;
	// }
	//
	// public void setShortName(String shortName) {
	// this.shortName = shortName;
	// }

	@Column(name = "ICON")
	// @DynamoDBAttribute(attributeName = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", pluralName="
				+ pluralName + ", icon=" + icon + "]";
	}

}