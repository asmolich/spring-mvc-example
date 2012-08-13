package com.travel.wifimap.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.travel.wifimap.dao.CommentDao;
import com.travel.wifimap.domain.Comment;

@Repository
public class CommentDaoImpl extends CRUDImpl<Comment> implements CommentDao {

	@Override
	public Class<Comment> getClazz() {
		return Comment.class;
	}

	@Override
	public Comment findBySourceId(String source, String sourceId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from Comment v where v.source = :source and v.sourceId = :sourceId");

		query.setString("source", source);
		query.setString("sourceId", sourceId);

		List<Comment> comments = query.list();
		if (comments != null && comments.size() > 0) {
			Comment comment = (Comment) comments.get(0);
			// Assert.isTrue(users.size() == 1);
			return comment;
		}
		return null;
	}

	// public void batchSave(List<? extends Object> objectsToSave) {
	// getDynamoDBMapper().batchSave(objectsToSave);
	// }

}