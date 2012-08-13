package com.travel.wifimap.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.VenueDao;
import com.travel.wifimap.domain.Comment;
import com.travel.wifimap.domain.Venue;

@Repository
public class VenueDaoImpl extends CRUDImpl<Venue> implements VenueDao {

	@Override
	public Class<Venue> getClazz() {
		return Venue.class;
	}

	// @Override
	// public void batchSave(List<? extends Object> objectsToSave) {
	// getDynamoDBMapper().batchSave(objectsToSave);
	// }

	@Override
	public Venue findBySourceId(String source, String sourceId) {

		// DynamoDBScanExpression scan = new DynamoDBScanExpression();
		// Condition condition = new Condition().withComparisonOperator(
		// ComparisonOperator.EQ.toString()).withAttributeValueList(
		// new AttributeValue().withS(source));
		// scan.addFilterCondition("SOURCE", condition);
		// condition = new Condition().withComparisonOperator(
		// ComparisonOperator.EQ.toString()).withAttributeValueList(
		// new AttributeValue().withS(sourceId));
		// scan.addFilterCondition("SOURCE_ID", condition);
		//
		// List<Venue> venues = getDynamoDBMapper().scan(Venue.class, scan);

		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from Venue v where v.source = :source and v.sourceId = :sourceId");

		query.setString("source", source);
		query.setString("sourceId", sourceId);

		List<Venue> venues = query.list();
		if (venues != null && venues.size() > 0) {
			Venue venue = (Venue) venues.get(0);
			// Assert.isTrue(users.size() == 1);
			return venue;
		}
		return null;
	}

	@Override
	public List<Comment> getVenueComments(String venueId) {
		Query query = getSessionFactory().getCurrentSession().createQuery(
				"select v.comments from Venue v where v.id = :venueId");

		query.setString("venueId", venueId);

		List<Comment> comments = query.list();
		return comments;
	}

	@Override
	public List<Venue> search(Double lat, Double lng, Double radius,
			Integer limit) {

		if (radius == null)
			radius = 100.0;// km
		radius = radius / 111.64; // degrees
		if (limit == null)
			limit = 500;
		// select *
		// from Venue v
		// where
		// ((v.latitude - :lat) < :radius) and
		// ((v.longitude - :lng) < :radius)
		// order by ((v.latitude - :lat)*(v.latitude - :lat)+(v.longitude -
		// :lng)*(v.longitude - :lng))
		// limit :limit

		// String queryString =
		// "from Venue v, ((v.latitude - :lat) * (v.latitude - :lat) + (v.longitude - :lng) * (v.longitude - :lng)) as distance where ((v.latitude - :lat) < :radius) and ((v.longitude - :lng) < :radius) order by distance asc";
		// Query query = getSessionFactory().getCurrentSession().createQuery(
		// queryString);
		//
		// query.setDouble("lat", lat);
		// query.setDouble("lng", lng);
		// query.setDouble("radius", radius);
		// query.setMaxResults(limit);
		//
		// List<Venue> venues = query.list();

		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(
				Venue.class);
		crit.add(Restrictions.between("latitude", lat - radius, lat + radius));
		crit.add(Restrictions.between("longitude", lng - radius, lng + radius));
		// Projections.projectionList()
		// .add(Projections
		// .sqlProjection(
		// "((latitude - :lat) * (latitude - :lat) + (longitude - :lng) * (longitude - :lng)) as distance",
		// new String[] { "distance" },
		// new Type[] { StandardBasicTypes.DOUBLE })));
		// crit.addOrder(Order.asc("distance"));
		crit.setMaxResults(limit);
		List<Venue> venues = crit.list();
		return venues;
	}
}
