package com.travel.wifimap.dao.impl;

import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.CheckinDao;
import com.travel.wifimap.domain.Checkin;

@Repository
public class CheckinDaoImpl extends CRUDImpl<Checkin> implements CheckinDao {

	// @Override
	// public List<Checkin> findCheckinsOfUser(String userId) {
	//
	// Condition condition = new Condition().withComparisonOperator(
	// ComparisonOperator.EQ.toString()).withAttributeValueList(
	// new AttributeValue().withS(userId));
	// DynamoDBScanExpression scan = new DynamoDBScanExpression();
	// scan.addFilterCondition("USER_ID", condition);
	//
	// List<Checkin> checkins = getDynamoDBMapper().scan(Checkin.class, scan);
	// return checkins;
	// }
	//
	// @Override
	// public List<Checkin> findCheckinsInVenue(String venueId) {
	//
	// Condition condition = new Condition().withComparisonOperator(
	// ComparisonOperator.EQ.toString()).withAttributeValueList(
	// new AttributeValue().withS(venueId));
	// DynamoDBScanExpression scan = new DynamoDBScanExpression();
	// scan.addFilterCondition("VENUE_ID", condition);
	//
	// List<Checkin> checkins = getDynamoDBMapper().scan(Checkin.class, scan);
	// return checkins;
	// }

	@Override
	public Class<Checkin> getClazz() {
		return Checkin.class;
	}

}
