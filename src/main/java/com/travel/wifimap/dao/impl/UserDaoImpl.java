package com.travel.wifimap.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.UserDao;
import com.travel.wifimap.domain.User;

@Repository
public class UserDaoImpl extends CRUDImpl<User> implements UserDao {

	@Override
	public User findUserByEmail(String email) {

		// Condition condition = new Condition().withComparisonOperator(
		// ComparisonOperator.EQ.toString()).withAttributeValueList(
		// new AttributeValue().withS(email));
		// DynamoDBScanExpression scan = new DynamoDBScanExpression();
		// scan.addFilterCondition("EMAIL", condition);
		//
		// List<User> users = getDynamoDBMapper().scan(User.class, scan);

		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from User u where u.email = :email");
		query.setString("email", email);
		List users = query.list();

		if (users != null && users.size() > 0) {
			User user = (User) users.get(0);
			// Assert.isTrue(users.size() == 1);
			return user;
		}
		return null;
	}

	@Override
	public User getUserByOAuthToken(String token) {

		// FIXME
		// Condition condition = new Condition().withComparisonOperator(
		// ComparisonOperator.EQ.toString()).withAttributeValueList(
		// new AttributeValue().withS(token));
		//
		// DynamoDBQueryExpression queryExpression = new
		// DynamoDBQueryExpression(
		// new AttributeValue().withS(token));
		//
		// queryExpression.setRangeKeyCondition(condition);
		//
		// List<User> users = getDynamoDBMapper().query(User.class,
		// queryExpression);

		List users = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select link.user from SocialProfile as link where link.oauthToken=?")
				.setString(0, token).list();

		if (users != null && users.size() > 0) {
			User user = (User) users.get(0);
			// Assert.isTrue(users.size() == 1);
			return user;
		}
		return null;
	}

	@Override
	public Class<User> getClazz() {
		return User.class;
	}
}
